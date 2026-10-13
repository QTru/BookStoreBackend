package iuh.fit.bookstorebackend.controllers;

import iuh.fit.bookstorebackend.models.Cart;
import iuh.fit.bookstorebackend.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{accountId}")
    public Cart getCart(@PathVariable String accountId) {
        return cartService.getOrCreate(accountId);
    }

    @PostMapping("/{accountId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public Cart addItem(@PathVariable String accountId, @RequestBody CartItemRequest request) {
        return cartService.add(accountId, request.bookId(), request.quantity());
    }

    @PutMapping("/{accountId}/items/{bookId}")
    public Cart updateItem(@PathVariable String accountId, @PathVariable String bookId,
                           @RequestBody QuantityRequest request) {
        return cartService.update(accountId, bookId, request.quantity());
    }

    @DeleteMapping("/{accountId}/items/{bookId}")
    public Cart removeItem(@PathVariable String accountId, @PathVariable String bookId) {
        return cartService.remove(accountId, bookId);
    }

    public record CartItemRequest(String bookId, int quantity) {}
    public record QuantityRequest(int quantity) {}
}
