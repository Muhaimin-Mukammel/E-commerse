package com.ecomerse.merchantmode.repository;

import com.ecomerse.merchantmode.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
}
