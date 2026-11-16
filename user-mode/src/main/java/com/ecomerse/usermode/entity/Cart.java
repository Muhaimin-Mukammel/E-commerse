package com.ecomerse.usermode.entity;

import com.ecomerse.usermode.status.CartStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartId")
    private Long cartId;

    @Column(name = "userId", nullable = false, unique = true)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    CartStatus status;

    @Column(name = "createdAt", nullable = false)
    Instant createdAt;

    @Column(name = "updatedAt")
    Instant updatedAt;

    @Column(name = "totalValue")
    BigDecimal totalValue;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();


    public Cart () {}

    public Cart(UUID user_id) {
        this.userId = user_id;
        this.status = CartStatus.ACTIVE;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.totalValue = BigDecimal.ZERO;
    }

    public Long getCart_id() {
        return cartId;
    }
    public void setCart_id(Long cartId) {
        this.cartId = cartId;
    }
    public UUID getUser_id() {
        return userId;
    }
    public void setUser_id(UUID userId) {
        this.userId = userId;
    }
    public CartStatus getStatus() {
        return status;
    }
    public void setStatus(CartStatus status) {
        this.status = status;
    }
    public Long getCartId() {
        return cartId;
    }
    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
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
    public BigDecimal getTotalValue() {
        return totalValue;
    }
    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }
    public List<CartItem> getCartItems() {
        return cartItems;
    }
    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
