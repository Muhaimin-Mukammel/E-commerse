package com.ecomerse.merchantmode.dto.stockReservationdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record ReserveRequest(
        @NotNull UUID productId,
        @NotNull @Positive Integer quantity
) {
}
