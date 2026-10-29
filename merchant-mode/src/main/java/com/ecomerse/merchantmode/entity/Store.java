package com.ecomerse.merchantmode.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeId", unique = true, nullable = false)
    private Long storeId;

    @Column(name = "publicStoreId", nullable = false)
    private UUID publicStoreId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchantId", nullable = false)
    private Merchant merchant;

    @Column(name = "storeName", nullable = false)
    private String storeName;

    @OneToOne(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private StoreContact storeContact;

    @OneToOne(mappedBy = "store",  cascade = CascadeType.ALL, orphanRemoval = true)
    private StoreAddress storeAddress;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    @CreationTimestamp
    @Column(name = "createdAt",  nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt",  nullable = false)
    private Instant updatedAt;

    public Store() {}

    public Store(Merchant merchant, String storeName) {
        this.publicStoreId = UUID.randomUUID();
        this.merchant = merchant;
        this.storeName = storeName;
    }

    public Long getStoreId() {
        return storeId;
    }
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
    public UUID getPublicStoreId() {
        return publicStoreId;
    }
    public void setPublicStoreId(UUID publicStoreId) {
        this.publicStoreId = publicStoreId;
    }
    public Merchant getMerchant() {
        return merchant;
    }
    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public StoreContact getStoreContact() {
        return storeContact;
    }
    public void setStoreContact(StoreContact storeContact) {
        this.storeContact = storeContact;
    }
    public StoreAddress getStoreAddress() {
        return storeAddress;
    }
    public void setStoreAddress(StoreAddress storeAddress) {
        this.storeAddress = storeAddress;
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
}
