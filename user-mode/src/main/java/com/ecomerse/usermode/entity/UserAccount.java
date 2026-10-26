package com.ecomerse.usermode.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @Column(name = "user_id")
    private UUID user_id;

    @Column(name = "clientId", nullable = false, unique = true)
    private UUID clientId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "email")
    private String email;

    @CreationTimestamp
    @Column(name = "creation_time")
    private Instant createdAt;

    public UserAccount(UUID clientId, String name, String email, String phone_number) {
        this.user_id = UUID.randomUUID();
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
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
    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public UUID getUser_id() {
        return user_id;
    }
    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }
    public UUID getClientId() {
        return clientId;
    }
    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }
}
