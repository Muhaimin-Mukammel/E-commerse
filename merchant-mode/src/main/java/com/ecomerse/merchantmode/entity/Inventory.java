package com.ecomerse.merchantmode.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table
public class Inventory {
    @Id
    @Column(name = "inventoryId")
    private UUID inventoryId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @Column(name = "stock", nullable = false)
    private Integer stock = 0;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false)
    private Instant updatedAt;

    @Column(name = "lastModifiedBy")
    private String lastModifiedBy;

    @Version
    @Column(name = "raw_version", nullable = false)
    private Integer raw_version;

    public Inventory() {}

    public Inventory(Product product, Integer stock) {
        this.inventoryId = UUID.randomUUID();
        this.product = product;
        this.stock = stock;
    }

    public UUID getInventoryId() {
        return inventoryId;
    }
    public void setInventoryId(UUID inventoryId) {
        this.inventoryId = inventoryId;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException(
                    "Stock cannot be null or negative"
            );
        }
        this.stock = stock;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
    public Integer getRaw_version() {
        return raw_version;
    }
    public void setRaw_version(Integer raw_version) {
        this.raw_version = raw_version;
    }
}
