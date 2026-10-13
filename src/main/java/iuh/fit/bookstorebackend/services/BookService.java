package iuh.fit.bookstorebackend.services;

import iuh.fit.bookstorebackend.models.Book;
import iuh.fit.bookstorebackend.repositories.BookRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class BookService extends CrudService<Book, String> {

    private final BookRepository bookRepository;

    private static final String[] HEADERS = {
            "id",
            "isbn",
            "title",
            "cover_image_url",
            "page_count",
            "description",
            "price",
            "version",
            "discount_percentage",
            "start_discount_date",
            "end_discount_date",
            "stock_quantity",
            "creation_date"
    };

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public BookService(BookRepository bookRepository) {
        super(bookRepository, "Book");
        this.bookRepository = bookRepository;
    }

    // ==================== EXPORT EXCEL ====================

    @Transactional(readOnly = true)
    public byte[] exportExcel() throws IOException {
        List<Book> books = bookRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Books");

            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            Row header = sheet.createRow(0);

            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(HEADERS[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIndex = 1;

            for (Book book : books) {
                Row row = sheet.createRow(rowIndex++);

                setCell(row, 0, book.getId());
                setCell(row, 1, book.getIsbn());
                setCell(row, 2, book.getTitle());
                setCell(row, 3, book.getCoverImageUrl());
                setCell(row, 4, book.getPageCount());
                setCell(row, 5, book.getDescription());
                setCell(row, 6, book.getPrice());
                setCell(row, 7, book.getVersion());
                setCell(row, 8, book.getDiscountPercentage());
                setCell(row, 9, book.getStartDiscountDate());
                setCell(row, 10, book.getEndDiscountDate());
                setCell(row, 11, book.getStockQuantity());
                setCell(row, 12, book.getCreationDate());
            }

            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(output);
            return output.toByteArray();
        }
    }

    private void setCell(Row row, int column, Object value) {
        Cell cell = row.createCell(column);

        if (value == null) {
            cell.setBlank();
        } else if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else if (value instanceof LocalDateTime) {
            cell.setCellValue(((LocalDateTime) value).format(DATE_FORMAT));
        } else {
            cell.setCellValue(value.toString());
        }
    }

    // ==================== IMPORT EXCEL ====================

    @Transactional
    public int importExcel(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Vui lòng chọn file Excel."
            );
        }

        List<Book> books = new ArrayList<>();

        try (InputStream input = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(input)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            // Bỏ qua dòng tiêu đề.
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row == null || isEmptyRow(row, formatter)) {
                    continue;
                }

                int excelRow = i + 1;

                String isbn = getValue(row, 1, formatter);
                String title = getValue(row, 2, formatter);
                String coverImageUrl = getValue(row, 3, formatter);
                String description = getValue(row, 5, formatter);

                if (isbn.isBlank() || title.isBlank()
                        || coverImageUrl.isBlank()
                        || description.isBlank()) {
                    throw new IllegalArgumentException(
                            "Thiếu dữ liệu bắt buộc tại dòng Excel "
                                    + excelRow
                    );
                }

                Book book = new Book();

                // ID và version do JPA quản lý.
                book.setIsbn(isbn);
                book.setTitle(title);
                book.setCoverImageUrl(coverImageUrl);
                book.setPageCount(
                        parseInt(row, 4, formatter, excelRow)
                );
                book.setDescription(description);
                book.setPrice(
                        parseDecimal(row, 6, formatter, excelRow)
                );
                book.setDiscountPercentage(
                        parseDecimal(row, 8, formatter, excelRow)
                );
                book.setStartDiscountDate(
                        parseDateTime(row, 9, formatter, excelRow)
                );
                book.setEndDiscountDate(
                        parseDateTime(row, 10, formatter, excelRow)
                );
                book.setStockQuantity(
                        parseInt(row, 11, formatter, excelRow)
                );

                // @PrePersist sẽ tự thiết lập creationDate.
                // Không nhập ID, version, creationDate từ Excel.

                validateBook(book, excelRow);
                books.add(book);
            }
        }

        if (books.isEmpty()) {
            throw new IllegalArgumentException(
                    "File Excel không có dữ liệu sách."
            );
        }

        // Lưu toàn bộ danh sách.
        // Nếu có lỗi database, transaction sẽ rollback.
        bookRepository.saveAll(books);

        return books.size();
    }

    private String getValue(
            Row row, int column, DataFormatter formatter) {

        Cell cell = row.getCell(
                column, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL
        );

        return cell == null
                ? ""
                : formatter.formatCellValue(cell).trim();
    }

    private boolean isEmptyRow(
            Row row, DataFormatter formatter) {

        for (int i = 0; i < HEADERS.length; i++) {
            if (!getValue(row, i, formatter).isBlank()) {
                return false;
            }
        }

        return true;
    }

    private int parseInt(
            Row row,
            int column,
            DataFormatter formatter,
            int excelRow) {

        String value = getValue(row, column, formatter);

        try {
            return new BigDecimal(value).intValueExact();
        } catch (NumberFormatException | ArithmeticException e) {
            throw new IllegalArgumentException(
                    "Số nguyên không hợp lệ tại dòng " + excelRow
                            + ", cột " + HEADERS[column]
            );
        }
    }

    private BigDecimal parseDecimal(
            Row row,
            int column,
            DataFormatter formatter,
            int excelRow) {

        String value = getValue(row, column, formatter);

        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Số không hợp lệ tại dòng " + excelRow
                            + ", cột " + HEADERS[column]
            );
        }
    }

    private LocalDateTime parseDateTime(
            Row row,
            int column,
            DataFormatter formatter,
            int excelRow) {

        String value = getValue(row, column, formatter);

        if (value.isBlank()) {
            return null;
        }

        try {
            return LocalDateTime.parse(value, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Ngày giờ phải có dạng yyyy-MM-ddTHH:mm:ss "
                            + "tại dòng " + excelRow
                            + ", cột " + HEADERS[column]
            );
        }
    }

    private void validateBook(Book book, int excelRow) {
        if (book.getPageCount() <= 0) {
            throw new IllegalArgumentException(
                    "page_count phải lớn hơn 0 tại dòng " + excelRow
            );
        }

        if (book.getStockQuantity() < 0) {
            throw new IllegalArgumentException(
                    "stock_quantity không được âm tại dòng " + excelRow
            );
        }

        if (book.getPrice() == null
                || book.getPrice().signum() < 0) {
            throw new IllegalArgumentException(
                    "price không hợp lệ tại dòng " + excelRow
            );
        }

        BigDecimal discount = book.getDiscountPercentage();

        if (discount == null
                || discount.signum() < 0
                || discount.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException(
                    "discount_percentage phải từ 0 đến 100 tại dòng "
                            + excelRow
            );
        }

        if (book.getStartDiscountDate() != null
                && book.getEndDiscountDate() != null
                && book.getEndDiscountDate()
                .isBefore(book.getStartDiscountDate())) {
            throw new IllegalArgumentException(
                    "end_discount_date phải sau start_discount_date "
                            + "tại dòng " + excelRow
            );
        }
    }
}
