package com.gisoo.marketplace.payment;

import com.gisoo.marketplace.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Payment> create(@Valid @RequestBody CreatePaymentRequest request) {
        Payment payment = paymentService.create(
                request.orderId(), request.amount(), request.transactionReference());
        return ApiResponse.success("Payment created", payment);
    }

    @GetMapping("/order/{orderId}")
    public ApiResponse<List<Payment>> forOrder(@PathVariable Long orderId) {
        return ApiResponse.success("Payments retrieved", paymentService.forOrder(orderId));
    }
}
