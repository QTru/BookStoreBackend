package iuh.fit.bookstorebackend.controllers;

import iuh.fit.bookstorebackend.models.OrderItem;
import iuh.fit.bookstorebackend.services.OrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {
    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public List<OrderItem> findAll() {
        return orderItemService.findAll();
    }

    @GetMapping("/{bookId}/{orderId}")
    public OrderItem findById(
            @PathVariable("bookId") String bookId,
            @PathVariable("orderId") String orderId
    ) {
        return orderItemService.findById(new OrderItem.OrderItemId(bookId, orderId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderItem create(@RequestBody OrderItem orderItem) {
        return orderItemService.save(orderItem);
    }

    @PutMapping("/{bookId}/{orderId}")
    public OrderItem update(
            @PathVariable("bookId") String bookId,
            @PathVariable("orderId") String orderId,
            @RequestBody OrderItem orderItem
    ) {
        if (orderItem.getBook() == null || orderItem.getOrder() == null
                || !bookId.equals(orderItem.getBook().getId())
                || !orderId.equals(orderItem.getOrder().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order item IDs must match the URL");
        }

        return orderItemService.update(new OrderItem.OrderItemId(bookId, orderId), orderItem);
    }

    @DeleteMapping("/{bookId}/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("bookId") String bookId,
            @PathVariable("orderId") String orderId
    ) {
        orderItemService.deleteById(new OrderItem.OrderItemId(bookId, orderId));
    }
}
