package com.ecomerse.usermode.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItemId")
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",  nullable = false)
    private Order order;

    @Column(name = "product_id", nullable = false)
    private UUID product_id;

    @Column(name = "product_name", nullable = false)
    private  String product_name;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unit_price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public OrderItem() {}

    public OrderItem(Order order, UUID product_id, String product_name, BigDecimal unit_price, Integer quantity) {
        this.order = order;
        this.product_id = product_id;
        this.product_name = product_name;
        this.unit_price = unit_price;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem orderItem)) return false;
        return Objects.equals(orderItemId, orderItem.orderItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderItemId);
    }

    public BigDecimal getLineTotal() {
        if (unit_price == null || quantity == null) {
            return BigDecimal.ZERO;
        }
        return unit_price.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getOrder_item_id() {
        return orderItemId;
    }
    public void setOrder_item_id(Long order_item_id) {
        this.orderItemId = order_item_id;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public UUID getProduct_id() {
        return product_id;
    }
    public void setProduct_id(UUID product_id) {
        this.product_id = product_id;
    }
    public String getProduct_name() {
        return product_name;
    }
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    public BigDecimal getUnit_price() {
        return unit_price;
    }
    public void setUnit_price(BigDecimal unit_price) {
        this.unit_price = unit_price;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
