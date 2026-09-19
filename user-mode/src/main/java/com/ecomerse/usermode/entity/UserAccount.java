package com.ecomerse.usermode.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    @Column(name = "keycloakId", nullable = false, unique = true)
    private String keycloakId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number")
    private Integer phone_number;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "email")
    private String email;

    @CreationTimestamp
    @Column(name = "creation_time")
    private Instant createdAt;

    public UserAccount(String keycloakId, String name, String email, Integer phone_number) {
        this.keycloakId = keycloakId;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
    }
}
