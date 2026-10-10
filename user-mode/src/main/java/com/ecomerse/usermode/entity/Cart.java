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
    @Column(name = "cart_id")
    private Long cart_id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID user_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    CartStatus status;

    @Column(name = "creation_time", nullable = false)
    Instant created_at;

    @Column(name = "last_update_time")
    Instant updated_at;

    @Column(name = "total_value")
    BigDecimal total_value;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column
    private List<CartItem> cart_items = new ArrayList<>();


    public Cart () {}

    public Cart(UUID user_id) {
        this.user_id = user_id;
        this.status = CartStatus.ACTIVE;
        this.created_at = Instant.now();
        this.updated_at = Instant.now();
        this.total_value = BigDecimal.ZERO;
    }

    public Long getCart_id() {
        return cart_id;
    }
    public void setCart_id(Long cart_id) {
        this.cart_id = cart_id;
    }
    public UUID getUser_id() {
        return user_id;
    }
    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }
    public CartStatus getStatus() {
        return status;
    }
    public void setStatus(CartStatus status) {
        this.status = status;
    }
    public Instant getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }
    public Instant getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(Instant updated_at) {
        this.updated_at = updated_at;
    }
    public BigDecimal getTotal_value() {
        return total_value;
    }
    public void setTotal_value(BigDecimal total_value) {
        this.total_value = total_value;
    }
    public List<CartItem> getCart_items() {
        return cart_items;
    }
    public void setCart_items(List<CartItem> cart_items) {
        this.cart_items = cart_items;
    }
}
