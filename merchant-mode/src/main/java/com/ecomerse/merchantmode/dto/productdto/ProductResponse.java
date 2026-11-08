package com.ecomerse.merchantmode.dto.productdto;

import com.ecomerse.merchantmode.status.ProductStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductResponse(
        UUID productId,
        String productName,
        BigDecimal productPrice,
        ProductStatus status,
        String description,
        Instant createdAt,
        Instant updatedAt,
        String lastModifiedBy
) {
}
