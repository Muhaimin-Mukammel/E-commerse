package com.ecomerse.merchantmode.dto.stockReservationdto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ConfirmReservationRequest(
        @NotNull UUID reservationId,
        @NotNull UUID orderId
) {
}
