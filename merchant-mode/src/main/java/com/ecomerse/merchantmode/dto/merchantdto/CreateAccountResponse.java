package com.ecomerse.merchantmode.dto.merchantdto;

import java.time.Instant;
import java.util.UUID;

public record CreateAccountResponse(
        UUID merchantId,
        String merchantName,
        String merchantEmail,
        Instant createdAt,
        Instant updatedAt,
        StoreInfo store
) {
    public record StoreInfo(
            Long storeId,
            UUID publicStoreId,
            String storeName,
            StoreContactInfo contact,
            StoreAddressInfo address
    ) {}

    public record StoreContactInfo(
            String storePhone,
            String storeEmail,
            String storeWebsiteLink
    ) {}

    public record StoreAddressInfo(
            String storeCountry,
            String storeCity,
            String storeRegion,
            String storePostalCode
    ) {}
}
