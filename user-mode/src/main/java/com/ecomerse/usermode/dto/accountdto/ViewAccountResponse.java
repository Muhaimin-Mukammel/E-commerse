package com.ecomerse.usermode.dto.accountdto;

import java.time.Instant;

public record ViewAccountResponse(
        Long id,
        String keycloakId,
        String name,
        String email,
        String phone_number,
        boolean isActive,
        Instant createdAt
) {
}
