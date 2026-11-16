package com.ecomerse.merchantmode.entity;

import com.ecomerse.merchantmode.status.ReservationStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "stockReservation")
public class StockReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ReservationId;

    @Column(name = "productId", nullable = false)
    private UUID productId;

    @Column(name = "publicStoreId",  nullable = false)
    private UUID publicStoreId;

    @Column(name = "clientId",  nullable = false)
    private UUID clientId;

    @Column(name = "quantity",  nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservationStatus",  nullable = false)
    private ReservationStatus reservationStatus;

    @Column(name = "orderId",   nullable = false)
    private UUID orderId;

    @Column(name = "expiredAt",   nullable = false)
    private Instant expiredAt;

    @CreationTimestamp
    @Column(name = "createdAt",  nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt",  nullable = false)
    private Instant updatedAt;

    public StockReservation() {}

    public StockReservation(UUID productId, UUID publicStoreId, UUID clientId, Integer quantity, Instant expiredAt){
        this.ReservationId = UUID.randomUUID();
        this.productId = productId;
        this.publicStoreId = publicStoreId;
        this.clientId = clientId;
        this.quantity = quantity;
        this.expiredAt = expiredAt;
        this.reservationStatus = ReservationStatus.ACTIVE;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();

    }

    public void confirm(UUID orderId){
        if(this.reservationStatus != ReservationStatus.ACTIVE){
            throw new IllegalStateException("Only ACTIVE reservations can be confirmed");
        }

        this.reservationStatus = ReservationStatus.CONFIRMED;
        this.orderId = orderId;
    }

    public void release(){
        if(this.reservationStatus != ReservationStatus.ACTIVE){
            throw new IllegalStateException("Only ACTIVE reservations can be released");
        }
        this.reservationStatus = ReservationStatus.RELEASED;
    }

    public void expire(){
        if(this.reservationStatus != ReservationStatus.ACTIVE){
            throw new IllegalStateException("Only ACTIVE reservations can be expired");
        }
        this.reservationStatus = ReservationStatus.EXPIRED;
    }

    public boolean isExpired(){
        return Instant.now().isAfter(this.expiredAt);
    }

    public UUID getReservationId() {
        return ReservationId;
    }
    public void setReservationId(UUID reservationId) {
        ReservationId = reservationId;
    }
    public UUID getProductId() {
        return productId;
    }
    public void setProductId(UUID productId) {
        this.productId = productId;
    }
    public UUID getPublicStoreId() {
        return publicStoreId;
    }
    public void setPublicStoreId(UUID publicStoreId) {
        this.publicStoreId = publicStoreId;
    }
    public UUID getUserId() {
        return clientId;
    }
    public void setUserId(UUID clientId) {
        this.clientId = clientId;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }
    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
    public UUID getOrderId() {
        return orderId;
    }
    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
    public Instant getExpiredAt() {
        return expiredAt;
    }
    public void setExpiredAt(Instant expiredAt) {
        this.expiredAt = expiredAt;
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
