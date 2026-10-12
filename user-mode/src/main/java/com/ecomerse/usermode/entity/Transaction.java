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
    @Column(name = "transaction_id")
    private Long transaction_id;

    @Column(name = "order_id",  nullable = false)
    private UUID order_id;

    @Column(name = "ammout",  nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_method", nullable = false)
    private TransactionMethod transactio_method;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status",  nullable = false)
    private TransactionStatus transaction_status;

    @Column(name = "created_at",   nullable = false)
    private Instant created_at;

    @Column(name = "completed_at")
    private Instant completed_at;

    public Transaction () {}

    public Transaction(UUID order_id, BigDecimal amount,  TransactionMethod transactio_method) {
        this.order_id = order_id;
        this.amount = amount;
        this.transactio_method = transactio_method;
        this.transaction_status = TransactionStatus.PENDING;
        this.created_at = Instant.now();
    }

    public Long getTransaction_id() {
        return transaction_id;
    }
    public void setTransaction_id(Long transaction_id) {
        this.transaction_id = transaction_id;
    }
    public UUID getOrder_id() {
        return order_id;
    }
    public void setOrder_id(UUID order_id) {
        this.order_id = order_id;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public TransactionMethod getTransactio_method() {
        return transactio_method;
    }
    public void setTransactio_method(TransactionMethod transactio_method) {
        this.transactio_method = transactio_method;
    }
    public TransactionStatus getTransaction_status() {
        return transaction_status;
    }
    public void setTransaction_status(TransactionStatus transaction_status) {
        this.transaction_status = transaction_status;
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
