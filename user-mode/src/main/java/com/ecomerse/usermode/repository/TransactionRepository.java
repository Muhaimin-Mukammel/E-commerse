package com.ecomerse.usermode.repository;

import com.ecomerse.usermode.entity.Transaction;
import com.ecomerse.usermode.status.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByOrder_id(UUID order_id);
    Optional<Transaction> findByTransaction_idAndOrder_id(Long transaction_id, UUID order_id);
    boolean existsByOrderIdAndStatus(UUID orderId, TransactionStatus status);
}
