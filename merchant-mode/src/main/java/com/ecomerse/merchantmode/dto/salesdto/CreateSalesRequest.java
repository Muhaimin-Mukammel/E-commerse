package com.ecomerse.merchantmode.dto.salesdto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CreateSalesRequest(
        Instant periodStart,
        Instant periodEnd,
        List<SalesItemRequest> items
) {
    public record SalesItemRequest(
            UUID productId,
            String productName,
            Integer quantitySold,
            BigDecimal totalPrice
    ) {}
}
