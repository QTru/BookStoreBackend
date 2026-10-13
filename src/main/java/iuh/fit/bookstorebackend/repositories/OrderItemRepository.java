package iuh.fit.bookstorebackend.repositories;

import iuh.fit.bookstorebackend.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItem.OrderItemId> {
}
