package com.ecomerse.merchantmode.dto.stockReservationdto;

import com.ecomerse.merchantmode.status.ReservationStatus;

import java.time.Instant;
import java.util.UUID;

public record ReservationResponse(
        UUID reservationId,
        UUID productId,
        Integer quantity,
        ReservationStatus status,
        Instant expiresAt,
        Instant createdAt
) {
}
