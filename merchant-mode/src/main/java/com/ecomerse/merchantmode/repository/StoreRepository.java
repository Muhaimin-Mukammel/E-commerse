package com.ecomerse.merchantmode.repository;

import com.ecomerse.merchantmode.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByPublicStoreId(UUID publicStoreId);
}
