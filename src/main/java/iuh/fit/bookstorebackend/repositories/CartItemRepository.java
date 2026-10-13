package iuh.fit.bookstorebackend.repositories;

import iuh.fit.bookstorebackend.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, String> {
    Optional<CartItem> findByCartIdAndBookId(String cartId, String bookId);
}
