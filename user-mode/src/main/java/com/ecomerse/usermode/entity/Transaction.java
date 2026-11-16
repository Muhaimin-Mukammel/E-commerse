package com.ecomerse.usermode.entity;

import com.ecomerse.usermode.status.TransactionMethod;
import com.ecomerse.usermode.status.TransactionStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionId")
    private Long transactionId;

    @Column(name = "orderId",  nullable = false)
    private UUID orderId;

    @Column(name = "amount",  nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transactionMethod", nullable = false)
    private TransactionMethod transactionMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",  nullable = false)
    private TransactionStatus status;

    @Column(name = "createdAt",   nullable = false)
    private Instant createdAt;

    @Column(name = "completedAt")
    private Instant completedAt;

    public Transaction () {}

    public Transaction(UUID order_id, BigDecimal amount,  TransactionMethod transactionMethod) {
        this.orderId = order_id;
        this.amount = amount;
        this.transactionMethod = transactionMethod;
        this.status = TransactionStatus.PENDING;
        this.createdAt = Instant.now();
    }

    public Long getTransaction_id() {
        return transactionId;
    }
    public void setTransaction_id(Long transaction_id) {
        this.transactionId = transaction_id;
    }
    public UUID getOrder_id() {
        return orderId;
    }
    public void setOrder_id(UUID order_id) {
        this.orderId = order_id;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public Long getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
    public UUID getOrderId() {
        return orderId;
    }
    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
    public TransactionMethod getTransactionMethod() {
        return transactionMethod;
    }
    public void setTransactionMethod(TransactionMethod transactionMethod) {
        this.transactionMethod = transactionMethod;
    }
    public TransactionStatus getStatus() {
        return status;
    }
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public Instant getCompletedAt() {
        return completedAt;
    }
    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }
}
