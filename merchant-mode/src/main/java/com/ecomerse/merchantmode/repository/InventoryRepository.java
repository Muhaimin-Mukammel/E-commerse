package com.ecomerse.merchantmode.repository;

import com.ecomerse.merchantmode.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    Optional<Inventory> findByClientId(UUID clientId);
    Optional<Inventory> findByProductId(UUID productId);
}
