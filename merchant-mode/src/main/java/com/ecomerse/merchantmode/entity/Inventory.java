package com.ecomerse.merchantmode.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @Column(name = "inventoryId")
    private UUID inventoryId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @Column(name = "on_hand", nullable = false)
    private int onHand = 0;

    @Column(name = "reserved", nullable = false)
    private int reserved = 0;

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

    public Inventory(Product product, Integer onHand) {
        this.inventoryId = UUID.randomUUID();
        this.product = product;
        this.onHand = onHand;
        this.reserved = 0;
    }

    public int getAvailable() {
        return onHand - reserved;
    }

    public void restock(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Restock quantity must be positive");
        this.onHand += quantity;
    }

    public void reserve(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Reserve quantity must be positive");
        if (getAvailable() < quantity) {
            throw new IllegalStateException("Not enough available stock");
        }
        this.reserved += quantity;
    }

    public void confirmReservation(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (this.reserved < quantity) {
            throw new IllegalStateException("Not enough reserved stock to confirm");
        }
        this.reserved -= quantity;
        this.onHand -= quantity;
    }

    public void releaseReservation(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (this.reserved < quantity) {
            throw new IllegalStateException("Not enough reserved stock to release");
        }
        this.reserved -= quantity;
    }

    public UUID getInventoryId() { return inventoryId; }
    public Product getProduct() { return product; }
    public int getOnHand() { return onHand; }
    public int getReserved() { return reserved; }
    public Integer getRaw_Version() { return raw_version; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public String getLastModifiedBy() { return lastModifiedBy; }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
