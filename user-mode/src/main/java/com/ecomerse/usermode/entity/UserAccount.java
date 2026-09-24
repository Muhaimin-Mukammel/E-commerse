package com.ecomerse.usermode.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "user_accounts")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    protected UserAccount () {}

    public String getKeycloakId() {
        return keycloakId;
    }

    public void setKeycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Integer getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(Integer phone_number) {
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long Id) {
        id = Id;
    }
}
