package com.ecomerse.usermode.repository;

import com.ecomerse.usermode.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalCartRepository extends JpaRepository<Cart, Integer> {
}
