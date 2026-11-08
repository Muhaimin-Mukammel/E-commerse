package com.ecomerse.merchantmode.repository;

import com.ecomerse.merchantmode.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Long> {
}
