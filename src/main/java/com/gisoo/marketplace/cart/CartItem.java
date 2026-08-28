package com.gisoo.marketplace.cart;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "cart_items", uniqueConstraints = @UniqueConstraint(name = "uk_cart_customer_product", columnNames = {"customer_id", "product_id"}))
public class CartItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private Long customerId;
    @Column(nullable = false) private Long productId;
    @Column(nullable = false) private Integer quantity;
    @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();

    protected CartItem() {}
    public CartItem(Long customerId, Long productId, Integer quantity) {
        this.customerId = customerId; this.productId = productId; this.quantity = quantity;
    }
    public void addQuantity(int amount) { this.quantity += amount; }
    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public Long getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public Instant getCreatedAt() { return createdAt; }
}
