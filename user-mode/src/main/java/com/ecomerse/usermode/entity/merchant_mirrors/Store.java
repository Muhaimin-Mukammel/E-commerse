package com.ecomerse.usermode.entity.merchant_mirrors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "store")
public class Store {
    @Id
    @Column(name = "public_store_id")
    private UUID publicStoreId;

    @Column(name = "merchantId", nullable = false)
    private UUID merchantId;

    @Column(name = "storeName", nullable = false)
    String storeName;

    public Store() {}
    public Store(UUID publicStoreId, UUID merchantId, String storeName) {
        this.publicStoreId = publicStoreId;
        this.merchantId = merchantId;
        this.storeName = storeName;
    }

    public UUID getPublicStoreId() {
        return publicStoreId;
    }
    public void setPublicStoreId(UUID publicStoreId) {
        this.publicStoreId = publicStoreId;
    }
    public UUID getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(UUID merchantId) {
        this.merchantId = merchantId;
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
