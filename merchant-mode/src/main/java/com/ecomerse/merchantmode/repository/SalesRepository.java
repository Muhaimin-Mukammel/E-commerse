package com.ecomerse.merchantmode.repository;

import com.ecomerse.merchantmode.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SalesRepository extends JpaRepository<Sales, UUID> {
}
