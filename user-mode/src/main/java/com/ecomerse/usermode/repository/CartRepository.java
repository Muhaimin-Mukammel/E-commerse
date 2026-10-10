package com.ecomerse.usermode.repository;

import com.ecomerse.usermode.entity.Cart;
import com.ecomerse.usermode.status.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCartId(String cartId);
    Optional<Cart> findByUser_idAndStatus(UUID userId, CartStatus cartStatus);
}
