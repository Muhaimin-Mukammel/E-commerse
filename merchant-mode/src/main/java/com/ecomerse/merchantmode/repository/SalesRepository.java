package com.ecomerse.merchantmode.repository;

import com.ecomerse.merchantmode.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    List<Sales> findByPublicStoreId(UUID publicStoreId);

}
