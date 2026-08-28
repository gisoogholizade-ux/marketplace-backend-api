package com.gisoo.marketplace.checkout;

import com.gisoo.marketplace.common.response.ApiResponse;
import com.gisoo.marketplace.order.MarketplaceOrder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/checkout")
public class CheckoutController {
    private final CheckoutService service;
    public CheckoutController(CheckoutService service) { this.service = service; }

    @PostMapping("/{customerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MarketplaceOrder> checkout(@PathVariable Long customerId) {
        return ApiResponse.success("Checkout completed", service.checkout(customerId));
    }
}
