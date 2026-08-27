package com.gisoo.marketplace.order;

import com.gisoo.marketplace.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MarketplaceOrder> create(@Valid @RequestBody CreateOrderRequest request) {
        return ApiResponse.success("Order created", orderService.create(request.customerId(), request.totalAmount()));
    }

    @GetMapping("/customer/{customerId}")
    public ApiResponse<List<MarketplaceOrder>> customerOrders(@PathVariable Long customerId) {
        return ApiResponse.success("Orders retrieved", orderService.forCustomer(customerId));
    }
}
