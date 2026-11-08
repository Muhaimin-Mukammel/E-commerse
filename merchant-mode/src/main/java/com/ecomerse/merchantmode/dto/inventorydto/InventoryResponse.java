package com.ecomerse.merchantmode.dto.inventorydto;

import java.time.Instant;
import java.util.UUID;

public record InventoryResponse(
        UUID inventoryId,
        UUID productId,
        Integer stock,
        Instant createdAt,
        Instant updatedAt,
        String lastModifiedBy
) {
}
