package com.ecomerse.usermode.dto.accountdto;

import java.time.Instant;
import java.util.UUID;

public record ViewAccountResponse(
        UUID id,
        String keycloakId,
        String name,
        String email,
        String phone_number,
        boolean isActive,
        Instant createdAt
) {
}
