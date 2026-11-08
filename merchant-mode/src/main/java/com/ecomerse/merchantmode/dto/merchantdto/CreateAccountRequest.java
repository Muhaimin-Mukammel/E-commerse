package com.ecomerse.merchantmode.dto.merchantdto;

public record CreateAccountRequest(
        String storeName,
        String storePhone,
        String storeEmail,
        String storeWebLink,
        String storeCountry,
        String storeCity,
        String storeRegion,
        String storePostalCode
) {}
