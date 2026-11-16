package com.ecomerse.merchantmode.dto.stockReservationdto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReleaseReservationRequest(
        @NotNull(message = "Reservation ID is required")
        UUID reservationId
) {
}
