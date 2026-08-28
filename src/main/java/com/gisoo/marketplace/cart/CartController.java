package com.gisoo.marketplace.cart;

import com.gisoo.marketplace.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService service;
    public CartController(CartService service) { this.service = service; }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CartItem> add(@Valid @RequestBody AddCartItemRequest request) {
        return ApiResponse.success("Item added to cart", service.add(request.customerId(), request.productId(), request.quantity()));
    }

    @GetMapping("/{customerId}")
    public ApiResponse<List<CartItem>> list(@PathVariable Long customerId) {
        return ApiResponse.success("Cart retrieved", service.list(customerId));
    }
}
