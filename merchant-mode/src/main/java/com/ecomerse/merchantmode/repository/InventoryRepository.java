package com.ecomerse.merchantmode.repository;

import com.ecomerse.merchantmode.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
}
