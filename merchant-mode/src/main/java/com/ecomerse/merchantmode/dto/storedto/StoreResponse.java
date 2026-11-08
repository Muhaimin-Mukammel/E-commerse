package com.ecomerse.merchantmode.dto.storedto;

import java.time.Instant;
import java.util.UUID;

public record StoreResponse(
        Long storeId,
        UUID publicStoreId,
        String storeName,
        Instant createdAt,
        Instant updatedAt,
        ContactInfo contact,
        AddressInfo address
) {
    public record ContactInfo(
            String storePhone,
            String storeEmail,
            String storeWebsiteLink
    ) {}
    public record AddressInfo(
            String storeCountry,
            String storeCity,
            String storeRegion,
            String storePostalCode
    ) {}
}
