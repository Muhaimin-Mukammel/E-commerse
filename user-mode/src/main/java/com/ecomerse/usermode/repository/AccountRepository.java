package com.ecomerse.usermode.repository;

import com.ecomerse.usermode.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmail(String email);
    Optional<UserAccount> findByKeycloakId(String keycloakId);

    void deleteByKeycloakId(String keycloakId);
}
