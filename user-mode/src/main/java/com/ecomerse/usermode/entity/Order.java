package com.ecomerse.usermode.entity;

import com.ecomerse.usermode.status.OrderPaymentStatus;
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
    @Column(name = "orderId")
    private UUID orderId;

    @Column(name = "userId", nullable = false)
    private UUID userId;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "createdAt",  nullable = false)
    private Instant createdAt;

    @Column(name = "cartId")
    private Long cartId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "orderPaymentStatus", nullable = false)
    private OrderPaymentStatus orderPaymentStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {}

    public Order(Long cart_id, UUID user_id) {
        orderId = UUID.randomUUID();
        this.userId = user_id;
        this.value = value;
        this.createdAt = Instant.now();
        this.status = OrderStatus.NOT_CONFIRMED;
        this.cartId = cart_id;
        this.orderPaymentStatus = OrderPaymentStatus.ORDER_NOT_CONFIRMED;
    }

    public void addItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }

    public void recalculateValue() {
        this.value = orderItems.stream()
                .map(OrderItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public OrderPaymentStatus getOrderPaymentStatus() {
        return orderPaymentStatus;
    }
    public void setOrderPaymentStatus(OrderPaymentStatus orderPaymentStatus) {
        this.orderPaymentStatus = orderPaymentStatus;
    }
    public UUID getOrder_id() {
        return orderId;
    }
    public void setOrder_id(UUID orderId) {
        this.orderId = orderId;
    }
    public UUID getUser_id() {
        return userId;
    }
    public void setUser_id(UUID userId) {
        this.userId = userId;
    }
    public BigDecimal getValue() {
        return value;
    }
    public void setValue(BigDecimal value) {
        this.value = value;
    }
    public UUID getOrderId() {
        return orderId;
    }
    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
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
    public Long getCartId() {
        return cartId;
    }
    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
