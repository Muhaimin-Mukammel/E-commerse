package com.ecomerse.usermode.entity;

import com.ecomerse.usermode.status.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    private UUID order_id;

    @Column(name = "user_id", nullable = false)
    private UUID user_id;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "created_at",  nullable = false)
    private Instant created_at;

    @Column(name = "cart_id")
    private Long cart_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {}

    public Order(Long cart_id, UUID user_id, BigDecimal value) {
        order_id = UUID.randomUUID();
        this.user_id = user_id;
        this.value = value;
        this.created_at = Instant.now();
        this.orderStatus = OrderStatus.NOT_CONFIRMED;
        this.cart_id = cart_id;
    }

    public UUID getOrder_id() {
        return order_id;
    }
    public void setOrder_id(UUID order_id) {
        this.order_id = order_id;
    }
    public UUID getUser_id() {
        return user_id;
    }
    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }
    public BigDecimal getValue() {
        return value;
    }
    public void setValue(BigDecimal value) {
        this.value = value;
    }
    public Instant getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }
    public Long getCart_id() {
        return cart_id;
    }
    public void setCart_id(Long cart_id) {
        this.cart_id = cart_id;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
