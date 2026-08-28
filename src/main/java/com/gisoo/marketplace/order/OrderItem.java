package com.gisoo.marketplace.order;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private Long orderId;
    @Column(nullable = false) private Long productId;
    @Column(nullable = false) private Long sellerId;
    @Column(nullable = false) private Integer quantity;
    @Column(nullable = false, precision = 19, scale = 2) private BigDecimal unitPrice;
    @Column(nullable = false, precision = 19, scale = 2) private BigDecimal lineTotal;

    protected OrderItem() {}
    public OrderItem(Long orderId, Long productId, Long sellerId, Integer quantity, BigDecimal unitPrice, BigDecimal lineTotal) {
        this.orderId = orderId; this.productId = productId; this.sellerId = sellerId; this.quantity = quantity; this.unitPrice = unitPrice; this.lineTotal = lineTotal;
    }
    public Long getId() { return id; }
    public Long getOrderId() { return orderId; }
    public Long getProductId() { return productId; }
    public Long getSellerId() { return sellerId; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getLineTotal() { return lineTotal; }
}
