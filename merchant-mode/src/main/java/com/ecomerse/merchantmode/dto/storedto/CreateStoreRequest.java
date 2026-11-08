package com.ecomerse.merchantmode.dto.storedto;

public record CreateStoreRequest(
        String storeName,
        String storePhone,
        String storeEmail,
        String storeWebLink,
        String storeCountry,
        String storeCity,
        String storeRegion,
        String storePostalCode
) {
}
