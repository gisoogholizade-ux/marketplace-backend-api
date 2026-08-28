package com.gisoo.marketplace.settlement;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "settlements")
public class Settlement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private Long sellerId;
    @Column(nullable = false, precision = 19, scale = 2) private BigDecimal amount;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 24) private SettlementStatus status = SettlementStatus.CREATED;
    @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();

    protected Settlement() {}
    public Settlement(Long sellerId, BigDecimal amount) { this.sellerId = sellerId; this.amount = amount; }
    public Long getId() { return id; }
    public Long getSellerId() { return sellerId; }
    public BigDecimal getAmount() { return amount; }
    public SettlementStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}
