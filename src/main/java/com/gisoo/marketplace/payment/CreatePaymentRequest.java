package com.gisoo.marketplace.payment;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreatePaymentRequest(
        @NotNull Long orderId,
        @NotNull @DecimalMin(value = "0.01") BigDecimal amount,
        String transactionReference
) {}
