package com.ecomerse.merchantmode.repository;

import com.ecomerse.merchantmode.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
