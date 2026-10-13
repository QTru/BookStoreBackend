package iuh.fit.bookstorebackend.services;

import iuh.fit.bookstorebackend.models.OrderItem;
import iuh.fit.bookstorebackend.repositories.OrderItemRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService extends CrudService<OrderItem, OrderItem.OrderItemId> {
    public OrderItemService(OrderItemRepository repository) {
        super(repository, "Order item");
    }
}
