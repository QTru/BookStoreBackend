package iuh.fit.bookstorebackend.services;

import iuh.fit.bookstorebackend.models.Book;
import iuh.fit.bookstorebackend.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private static final String CART_SESSION_ATTRIBUTE = "bookstore.cart";

    private final BookRepository bookRepository;

    public CartService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public SessionCart getCart(HttpSession session) {
        return new SessionCart(getItems(session).stream().map(item -> {
            Book book = findBook(item.bookId());
            return new CartLine(item.bookId(), book.getTitle(), book.getPrice(), item.quantity());
        }).toList());
    }

    public SessionCart add(String bookId, int quantity, HttpSession session) {
        validateQuantity(quantity);
        Book book = findBook(bookId);
        List<SessionCartItem> items = getItems(session);
        SessionCartItem item = findItem(items, bookId);
        int newQuantity = (item == null ? 0 : item.quantity()) + quantity;
        validateStock(book, newQuantity);

        if (item == null) {
            items.add(new SessionCartItem(bookId, quantity));
        } else {
            replace(items, item, new SessionCartItem(bookId, newQuantity));
        }
        saveItems(session, items);
        return getCart(session);
    }

    public SessionCart update(String bookId, int quantity, HttpSession session) {
        validateQuantity(quantity);
        Book book = findBook(bookId);
        List<SessionCartItem> items = getItems(session);
        SessionCartItem item = findItem(items, bookId);
        if (item == null) {
            throw new EntityNotFoundException("Book is not in the cart: " + bookId);
        }
        validateStock(book, quantity);
        replace(items, item, new SessionCartItem(bookId, quantity));
        saveItems(session, items);
        return getCart(session);
    }

    public SessionCart remove(String bookId, HttpSession session) {
        List<SessionCartItem> items = getItems(session);
        if (findItem(items, bookId) == null) {
            throw new EntityNotFoundException("Book is not in the cart: " + bookId);
        }
        items.removeIf(item -> item.bookId().equals(bookId));
        saveItems(session, items);
        return getCart(session);
    }

    private List<SessionCartItem> getItems(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<SessionCartItem> items = (List<SessionCartItem>) session.getAttribute(CART_SESSION_ATTRIBUTE);
        if (items == null) {
            items = new ArrayList<>();
            session.setAttribute(CART_SESSION_ATTRIBUTE, items);
        }
        return new ArrayList<>(items);
    }

    private void saveItems(HttpSession session, List<SessionCartItem> items) {
        session.setAttribute(CART_SESSION_ATTRIBUTE, new ArrayList<>(items));
    }

    private SessionCartItem findItem(List<SessionCartItem> items, String bookId) {
        return items.stream().filter(item -> item.bookId().equals(bookId)).findFirst().orElse(null);
    }

    private void replace(List<SessionCartItem> items, SessionCartItem oldItem, SessionCartItem newItem) {
        items.set(items.indexOf(oldItem), newItem);
    }

    private Book findBook(String bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + bookId));
    }

    private void validateQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }
    }

    private void validateStock(Book book, int quantity) {
        if (quantity > book.getStockQuantity()) {
            throw new IllegalArgumentException("Quantity exceeds stock for book: " + book.getId());
        }
    }

    public record SessionCart(List<CartLine> items) {
        public BigDecimal getTotal() {
            return items.stream()
                    .map(item -> item.lineTotal())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    public record SessionCartItem(String bookId, int quantity) {
    }

    public record CartLine(String bookId, String title, BigDecimal unitPrice, int quantity) {
        public BigDecimal lineTotal() {
            return unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
    }
}
