package com.gisoo.marketplace.payment;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 24)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(length = 120)
    private String transactionReference;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected Payment() {}

    public Payment(Long orderId, BigDecimal amount, String transactionReference) {
        this.orderId = orderId;
        this.amount = amount;
        this.transactionReference = transactionReference;
    }

    public Long getId() { return id; }
    public Long getOrderId() { return orderId; }
    public BigDecimal getAmount() { return amount; }
    public PaymentStatus getStatus() { return status; }
    public String getTransactionReference() { return transactionReference; }
    public Instant getCreatedAt() { return createdAt; }
}
