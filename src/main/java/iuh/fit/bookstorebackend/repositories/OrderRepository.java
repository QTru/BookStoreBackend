package iuh.fit.bookstorebackend.repositories;

import iuh.fit.bookstorebackend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
