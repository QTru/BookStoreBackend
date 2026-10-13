package iuh.fit.bookstorebackend.repositories;

import iuh.fit.bookstorebackend.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByAccountId(String accountId);
}
