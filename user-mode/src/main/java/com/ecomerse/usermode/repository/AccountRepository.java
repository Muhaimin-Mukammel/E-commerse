package com.ecomerse.usermode.repository;

import com.ecomerse.usermode.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<UserAccount, Integer> {
    Optional<UserAccount> findByEmail(String email);
    Optional<UserAccount> findByKeycloakId(String keycloakId);

    Optional<UserAccount> deleteByKeycloakId(String keycloakId);
}
