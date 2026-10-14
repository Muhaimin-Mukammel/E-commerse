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

    @Column(name = "ammout",  nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_method", nullable = false)
    private TransactionMethod transaction_method;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status",  nullable = false)
    private TransactionStatus status;

    @Column(name = "created_at",   nullable = false)
    private Instant created_at;

    @Column(name = "completed_at")
    private Instant completed_at;

    public Transaction () {}

    public Transaction(UUID order_id, BigDecimal amount,  TransactionMethod transactio_method) {
        this.orderId = order_id;
        this.amount = amount;
        this.transaction_method = transactio_method;
        this.status = TransactionStatus.PENDING;
        this.created_at = Instant.now();
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
    public TransactionMethod getTransactio_method() {
        return transaction_method;
    }
    public void setTransactio_method(TransactionMethod transactio_method) {
        this.transaction_method = transactio_method;
    }
    public TransactionStatus getTransaction_status() {
        return status;
    }
    public void setTransaction_status(TransactionStatus transaction_status) {
        this.status = transaction_status;
    }
    public Instant getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }
    public Instant getCompleted_at() {
        return completed_at;
    }
    public void setCompleted_at(Instant completed_at) {
        this.completed_at = completed_at;
    }
}
