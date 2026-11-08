package com.ecomerse.merchantmode.repository;

import com.ecomerse.merchantmode.entity.Product;
import com.ecomerse.merchantmode.status.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByPublicStoreId(UUID publicStoreId);
    List<Product> findByPublicStoreIdAndStatus(UUID publicStoreId, ProductStatus status);
}
