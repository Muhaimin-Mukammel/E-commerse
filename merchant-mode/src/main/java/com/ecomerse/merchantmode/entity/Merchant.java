package com.ecomerse.merchantmode.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table
public class Merchant {

    @Id
    @Column(name = "merchantId")
    private UUID merchantId;

    @Column(name = "merchantName")
    private String merchantName;

    @Column(name = "isActive")
    private boolean isActive;

    @Column(name = "merchantPhone")
    private String merchantPhone;

    @Column(name = "merchantEmail")
    private String merchantEmail;

    @CreationTimestamp
    @Column(name = "createdAt")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt")
    private Instant updatedAt;

    @OneToOne(mappedBy = "merchant",  cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "store")
    private Store store;

    public Merchant() {}

    public Merchant(String merchantName, String merchantPhone, String merchantEmail) {
        this.merchantId = UUID.randomUUID();
        this.merchantName = merchantName;
        this.merchantPhone = merchantPhone;
        this.merchantEmail = merchantEmail;
        this.isActive = true;
    }

    public UUID getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(UUID merchantId) {
        this.merchantId = merchantId;
    }
    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
    public String getMerchantPhone() {
        return merchantPhone;
    }
    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }
    public String getMerchantEmail() {
        return merchantEmail;
    }
    public void setMerchantEmail(String merchantEmail) {
        this.merchantEmail = merchantEmail;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Store getStore() {
        return store;
    }
    public void setStore(Store store) {this.store = store;}
}
