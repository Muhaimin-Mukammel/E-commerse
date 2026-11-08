package com.ecomerse.merchantmode.dto.salesdto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record SalesResponse(
        Long salesId,
        UUID publicStoreId,
        BigDecimal totalSales,
        Integer totalItemSold,
        Instant periodStart,
        Instant periodEnd,
        Instant createdAt,
        List<SalesItemInfo> items
) {
    public record SalesItemInfo(
            UUID productId,
            String productName,
            Integer quantitySold,
            BigDecimal totalPrice
    ) {}
}
