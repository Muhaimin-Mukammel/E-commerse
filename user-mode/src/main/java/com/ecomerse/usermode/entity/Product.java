package com.ecomerse.usermode.entity;

import com.ecomerse.usermode.status.ProductStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "name",  nullable = false)
    private String name;

    @Column(name = "product_price", nullable = false)
    private BigDecimal productPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_status", nullable = false)
    private ProductStatus productStatus;

    @Column(name = "category_id", nullable = false)
    private UUID categoryId;

    public Product() {}
    public Product(UUID productId, String name, BigDecimal productPrice, ProductStatus productStatus, UUID categoryId) {
        this.productId = productId;
        this.name = name;
        this.productPrice = productPrice;
        this.productStatus = productStatus;
        this.categoryId = categoryId;
    }

    public UUID getProductId() {
        return productId;
    }
    public void setProductId(UUID productId) {
        this.productId = productId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
    public ProductStatus getProductStatus() {
        return productStatus;
    }
    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }
    public UUID getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
}
