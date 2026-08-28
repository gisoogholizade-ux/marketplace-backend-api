package com.gisoo.marketplace.commission;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "commissions")
public class Commission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private Long orderId;
    @Column(nullable = false) private Long sellerId;
    @Column(nullable = false, precision = 19, scale = 2) private BigDecimal amount;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 24) private CommissionStatus status = CommissionStatus.PENDING;
    @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();

    protected Commission() {}
    public Commission(Long orderId, Long sellerId, BigDecimal amount) { this.orderId = orderId; this.sellerId = sellerId; this.amount = amount; }
    public void markSettled() { this.status = CommissionStatus.SETTLED; }
    public Long getId() { return id; }
    public Long getOrderId() { return orderId; }
    public Long getSellerId() { return sellerId; }
    public BigDecimal getAmount() { return amount; }
    public CommissionStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}
