package com.ecomerse.usermode.repository;

import com.ecomerse.usermode.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalTransactionRepository extends JpaRepository<Transaction, Integer> {
}
