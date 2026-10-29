package com.ecomerse.merchantmode.entity;

import com.ecomerse.merchantmode.status.ProductStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table
public class Product {
    @Id
    @Column(name = "productId")
    private UUID productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publicStoreId", nullable = false)
    private Store store;

    @Column(name = "productName",  nullable = false)
    private String productName;

    @Column(name = "productPrice", nullable = false)
    private BigDecimal productPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProductStatus status;

    @Column(name = "description", nullable = false)
    private String description;

    @CreationTimestamp
    @Column(name = "createdAt")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt")
    private Instant updatedAt;

    @Column(name = "lastModifiedBy", nullable = false)
    private String lastModifiedBy;

    @Version
    @Column(name = "raw_version", nullable = false)
    private Integer raw_version;

    public Product() {}

    public Product(Store store, String productName, BigDecimal productPrice, String description) {
        this.productId = UUID.randomUUID();
        this.store = store;
        this.productName = productName;
        this.productPrice = productPrice;
        this.status = ProductStatus.ACTIVE;
        this.description = description;
    }

    public UUID getProdcutId() {
        return productId;
    }
    public void setProdcutId(UUID prodcutId) {
        this.productId = prodcutId;
    }
    public Store getStore() {
        return store;
    }
    public void setStore(Store store) {
        this.store = store;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public BigDecimal getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
    public ProductStatus getStatus() {
        return status;
    }
    public void setStatus(ProductStatus status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
