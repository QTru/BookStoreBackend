package iuh.fit.bookstorebackend.services;

import iuh.fit.bookstorebackend.models.*;
import iuh.fit.bookstorebackend.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AccountRepository accountRepository;
    private final BookRepository bookRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
                       AccountRepository accountRepository, BookRepository bookRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.accountRepository = accountRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Cart getOrCreate(String accountId) {
        return cartRepository.findByAccountId(accountId).orElseGet(() -> {
            Account account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new EntityNotFoundException("Account not found: " + accountId));
            return cartRepository.save(Cart.builder().account(account).build());
        });
    }

    @Transactional
    public Cart add(String accountId, String bookId, int quantity) {
        validateQuantity(quantity);
        Cart cart = getOrCreate(accountId);
        Book book = findBook(bookId);
        CartItem item = cartItemRepository.findByCartIdAndBookId(cart.getId(), bookId)
                .orElseGet(() -> CartItem.builder().cart(cart).book(book).quantity(0).build());
        validateStock(book, item.getQuantity() + quantity);
        item.setQuantity(item.getQuantity() + quantity);
        cartItemRepository.save(item);
        return cart;
    }

    @Transactional
    public Cart update(String accountId, String bookId, int quantity) {
        validateQuantity(quantity);
        Cart cart = getOrCreate(accountId);
        CartItem item = cartItemRepository.findByCartIdAndBookId(cart.getId(), bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book is not in the cart: " + bookId));
        validateStock(item.getBook(), quantity);
        item.setQuantity(quantity);
        return cartItemRepository.save(item).getCart();
    }

    @Transactional
    public Cart remove(String accountId, String bookId) {
        Cart cart = getOrCreate(accountId);
        CartItem item = cartItemRepository.findByCartIdAndBookId(cart.getId(), bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book is not in the cart: " + bookId));
        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        return cart;
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
}
