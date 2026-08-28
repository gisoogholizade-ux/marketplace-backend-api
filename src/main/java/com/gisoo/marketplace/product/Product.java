package com.gisoo.marketplace.product;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 160)
    private String name;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Long sellerId;

    @Column
    private Long categoryId;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected Product() {}

    public Product(String name, BigDecimal price, Integer stock, Long sellerId) {
        this(name, price, stock, sellerId, null);
    }

    public Product(String name, BigDecimal price, Integer stock, Long sellerId, Long categoryId) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.sellerId = sellerId;
        this.categoryId = categoryId;
    }

    public void decreaseStock(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be greater than zero");
        if (stock < quantity) throw new IllegalArgumentException("Insufficient product stock");
        stock -= quantity;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public Integer getStock() { return stock; }
    public Long getSellerId() { return sellerId; }
    public Long getCategoryId() { return categoryId; }
    public boolean isActive() { return active; }
    public Instant getCreatedAt() { return createdAt; }
}
