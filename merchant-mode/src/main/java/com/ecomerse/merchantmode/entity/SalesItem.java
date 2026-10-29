package com.ecomerse.merchantmode.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
public class SalesItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salesItemId")
    private Long salesItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salesId", nullable = false)
    private Sales sales;

    @Column(name = "productId", nullable = false, updatable = false)
    private UUID productId;

    @Column(name = "productName", nullable = false, updatable = false)
    private String productName;

    @Column(name = "quantitySold", nullable = false)
    private Integer quantitySold;

    @Column(name = "totalPrice",  nullable = false)
    private BigDecimal totalPrice;

    @CreationTimestamp
    @Column(name = "createdAt")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt")
    private Instant updatedAt;

    public SalesItem() {}

    public SalesItem(Sales sales, String productName, Integer quantitySold, BigDecimal totalPrice) {
        this.sales = sales;
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.totalPrice = totalPrice;
    }

    public Long getSalesItemId() {
        return salesItemId;
    }
    public void setSalesItemId(Long salesItemId) {
        this.salesItemId = salesItemId;
    }
    public Sales getSales() {
        return sales;
    }
    public void setSales(Sales sales) {
        this.sales = sales;
    }
    public UUID getProductId() {
        return productId;
    }
    public void setProductId(UUID productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public Integer getQuantitySold() {
        return quantitySold;
    }
    public void setQuantitySold(Integer quantitySold) {
        if(quantitySold < 0) throw new IllegalArgumentException("quantitySold cannot be less than 0");
        this.quantitySold = quantitySold;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        if(totalPrice.signum() < 0) throw new IllegalArgumentException("totalPrice cannot be less than 0");
        this.totalPrice = totalPrice;
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
}
