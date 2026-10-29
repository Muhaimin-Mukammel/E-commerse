package com.ecomerse.merchantmode.entity;

import jakarta.persistence.*;

@Entity
@Table
public class StoreContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeContactId")
    private Long storeContactId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", unique = true, nullable = false)
    private Store store;

    @Column(name = "storePhone", nullable = false)
    private String storePhone;

    @Column(name = "storeEmail", nullable = false)
    private String storeEmail;

    @Column(name = "storeWebsiteLink", nullable = true)
    private String storeWebsiteLink;

    public StoreContact() {}

    public StoreContact(Store store, String storePhone, String storeEmail, String storeWebsiteLink) {
        this.store = store;
        this.storePhone = storePhone;
        this.storeEmail = storeEmail;
        this.storeWebsiteLink = storeWebsiteLink;
    }

    public Long getStoreContactId() {
        return storeContactId;
    }
    public void setStoreContactId(Long storeContactId) {
        this.storeContactId = storeContactId;
    }
    public Store getStore() {
        return store;
    }
    public void setStore(Store store) {
        this.store = store;
    }
    public String getStorePhone() {
        return storePhone;
    }
    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }
    public String getStoreEmail() {
        return storeEmail;
    }
    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
    }
    public String getStoreWebsiteLink() {
        return storeWebsiteLink;
    }
    public void setStoreWebsiteLink(String storeWebsiteLink) {
        this.storeWebsiteLink = storeWebsiteLink;
    }
}
