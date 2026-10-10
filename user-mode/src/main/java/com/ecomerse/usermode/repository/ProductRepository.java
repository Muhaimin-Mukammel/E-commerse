package com.ecomerse.usermode.repository;

import com.ecomerse.usermode.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {}
