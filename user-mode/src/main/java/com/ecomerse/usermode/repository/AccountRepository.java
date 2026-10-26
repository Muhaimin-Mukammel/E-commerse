package com.ecomerse.usermode.repository;

import com.ecomerse.usermode.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<UserAccount, UUID> {
    Optional<UserAccount> findByEmail(String email);
    Optional<UserAccount> findByClientId(UUID clientId);
    void deleteByClientId(UUID clientId);
}
