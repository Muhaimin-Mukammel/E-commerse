package com.ecomerse.usermode.repository;

import com.ecomerse.usermode.entity.Order;
import com.ecomerse.usermode.status.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findByOrder_idAndUser_id(UUID orderId,  UUID userId);
    Optional<Order> findBuUser_idAndStatus(UUID orderId, OrderStatus orderStatus);
}
