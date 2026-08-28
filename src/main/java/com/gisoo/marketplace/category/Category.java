package com.gisoo.marketplace.category;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(name = "uk_categories_slug", columnNames = "slug"))
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String name;
    @Column(nullable = false, length = 140)
    private String slug;
    @Column(nullable = false)
    private boolean active = true;
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected Category() {}
    public Category(String name, String slug) { this.name = name; this.slug = slug; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
    public boolean isActive() { return active; }
    public Instant getCreatedAt() { return createdAt; }
}
