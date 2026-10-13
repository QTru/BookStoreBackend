package iuh.fit.bookstorebackend.controllers;

import iuh.fit.bookstorebackend.services.CartService;
import jakarta.servlet.http.HttpSession;
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
    public CartService.SessionCart getCart(@PathVariable String accountId, HttpSession session) {
        return cartService.getCart(session);
    }

    @PostMapping("/{accountId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartService.SessionCart addItem(@PathVariable String accountId, @RequestBody CartItemRequest request,
                                           HttpSession session) {
        return cartService.add(request.bookId(), request.quantity(), session);
    }

    @PutMapping("/{accountId}/items/{bookId}")
    public CartService.SessionCart updateItem(@PathVariable String accountId, @PathVariable String bookId,
                           @RequestBody QuantityRequest request, HttpSession session) {
        return cartService.update(bookId, request.quantity(), session);
    }

    @DeleteMapping("/{accountId}/items/{bookId}")
    public CartService.SessionCart removeItem(@PathVariable String accountId, @PathVariable String bookId,
                                              HttpSession session) {
        return cartService.remove(bookId, session);
    }

    public record CartItemRequest(String bookId, int quantity) {}
    public record QuantityRequest(int quantity) {}
}
