package com.ecomerse.merchantmode.entity;

import jakarta.persistence.*;

@Entity
@Table
public class StoreAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeAddressId")
    private Long storeAddressId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", unique = true, nullable = false)
    private Store store;

    @Column(name = "storeCountry", nullable = false)
    private String storeCountry;

    @Column(name = "storeCity",  nullable = false)
    private String storeCity;

    @Column(name = "storeRegion",  nullable = false)
    private String storeRegion;

    @Column(name = "storePostalCode",   nullable = false)
    private String storePostalCode;

    public StoreAddress() {}

    public StoreAddress(Store store, String storeCountry, String storeCity, String storeRegion, String storePostalCode) {
        this.store = store;
        this.storeCountry = storeCountry;
        this.storeCity = storeCity;
        this.storeRegion = storeRegion;
        this.storePostalCode = storePostalCode;
    }

    public Long getStoreAddressId() {
        return storeAddressId;
    }
    public void setStoreAddressId(Long storeAddressId) {
        this.storeAddressId = storeAddressId;
    }
    public Store getStore() {
        return store;
    }
    public void setStore(Store store) {
        this.store = store;
    }
    public String getStoreCountry() {
        return storeCountry;
    }
    public void setStoreCountry(String storeCountry) {
        this.storeCountry = storeCountry;
    }
    public String getStoreCity() {
        return storeCity;
    }
    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
    }
    public String getStoreRegion() {
        return storeRegion;
    }
    public void setStoreRegion(String storeRegion) {
        this.storeRegion = storeRegion;
    }
    public String getStorePostalCode() {
        return storePostalCode;
    }
    public void setStorePostalCode(String storePostalCode) {
        this.storePostalCode = storePostalCode;
    }
}
