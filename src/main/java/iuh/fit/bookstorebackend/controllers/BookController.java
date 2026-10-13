package iuh.fit.bookstorebackend.controllers;

import iuh.fit.bookstorebackend.models.Book;
import iuh.fit.bookstorebackend.services.BookService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public Book findById(@PathVariable("id") String id) {
        return bookService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable("id") String id, @RequestBody Book book) {
        book.setId(id);
        return bookService.update(id, book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        bookService.deleteById(id);
    }

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportExcel() throws IOException {
        byte[] data = bookService.exportExcel();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=books.xlsx"
                )
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                ))
                .body(data);
    }

    @PostMapping(
            value = "/import/excel",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> importExcel(
            @RequestParam("file") MultipartFile file) throws IOException {

        int count = bookService.importExcel(file);

        return ResponseEntity.ok(
                "Nhập Excel thành công: " + count + " sách"
        );
    }
}
