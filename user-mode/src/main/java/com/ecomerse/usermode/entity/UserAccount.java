package com.ecomerse.usermode.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @Column(name = "userId")
    private UUID userId;

    @Column(name = "clientId", nullable = false)
    private UUID clientId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "isActive")
    private boolean isActive = true;

    @Column(name = "email")
    private String email;

    @CreationTimestamp
    @Column(name = "createdAt")
    private Instant createdAt;

    public UserAccount(UUID clientId, String name, String email, String phoneNumber) {
        this.userId = UUID.randomUUID();
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    protected UserAccount () {}
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
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public UUID getClientId() {
        return clientId;
    }
    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }
}
