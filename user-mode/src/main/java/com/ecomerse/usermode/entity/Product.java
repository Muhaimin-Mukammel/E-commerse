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
    private UUID product_id;

    @Column(name = "name",  nullable = false)
    private String name;

    @Column(name = "product_price", nullable = false)
    private BigDecimal product_price;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_status", nullable = false)
    private ProductStatus product_status;

    public Product() {}
    public Product(UUID product_id, String name, BigDecimal product_price, ProductStatus product_status) {
        this.product_id = product_id;
        this.name = name;
        this.product_price = product_price;
        this.product_status = product_status;
    }

    public UUID getProduct_id() {
        return product_id;
    }
    public void setProduct_id(UUID product_id) {
        this.product_id = product_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getProduct_price() {
        return product_price;
    }
    public void setProduct_price(BigDecimal product_price) {
        this.product_price = product_price;
    }
    public ProductStatus getProduct_status() {
        return product_status;
    }
    public void setProduct_status(ProductStatus product_status) {
        this.product_status = product_status;
    }
}
