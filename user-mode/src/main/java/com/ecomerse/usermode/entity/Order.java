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

    @Column(name = "created_at",  nullable = false)
    private Instant created_at;

    @Column(name = "cart_id")
    private Long cart_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_ayment_status", nullable = false)
    private OrderPaymentStatus orderPaymentStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> order_items = new ArrayList<>();

    public Order() {}

    public Order(Long cart_id, UUID user_id) {
        orderId = UUID.randomUUID();
        this.userId = user_id;
        this.value = value;
        this.created_at = Instant.now();
        this.status = OrderStatus.NOT_CONFIRMED;
        this.cart_id = cart_id;
        this.orderPaymentStatus = OrderPaymentStatus.ORDER_NOT_CONFIRMED;
    }

    public void addItem(OrderItem item) {
        order_items.add(item);
        item.setOrder(this);
    }

    public void recalculateValue() {
        this.value = order_items.stream()
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
        return status;
    }
    public void setOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }
    public List<OrderItem> getOrder_items() {
        return order_items;
    }
    public void setOrder_items(List<OrderItem> order_items) {
        this.order_items = order_items;
    }
}
