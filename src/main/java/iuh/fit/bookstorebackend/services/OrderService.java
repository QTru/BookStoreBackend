package iuh.fit.bookstorebackend.services;

import iuh.fit.bookstorebackend.models.Order;
import iuh.fit.bookstorebackend.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends CrudService<Order, String> {
    public OrderService(OrderRepository repository) {
        super(repository, "Order");
    }
}
