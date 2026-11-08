package com.ecomerse.merchantmode.dto.merchantdto;

public record UpdateAccountRequest(
        String storeName,
        String storePhone,
        String storeEmail,
        String storeWebsiteLink,
        String storeCountry,
        String storeCity,
        String storeRegion,
        String storePostalCode
) {}
