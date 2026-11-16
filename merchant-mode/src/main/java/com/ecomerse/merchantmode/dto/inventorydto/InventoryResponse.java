package com.ecomerse.merchantmode.dto.inventorydto;

import java.time.Instant;
import java.util.UUID;

public record InventoryResponse(
        UUID inventoryId,
        UUID productId,
        Integer onHand,
        Integer available,
        Integer reserved,
        Instant createdAt,
        Instant updatedAt,
        String lastModifiedBy
) {
}
