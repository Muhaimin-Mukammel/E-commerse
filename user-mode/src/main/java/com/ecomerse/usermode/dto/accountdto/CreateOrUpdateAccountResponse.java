package com.ecomerse.usermode.dto.accountdto;

import java.util.UUID;

public record CreateOrUpdateAccountResponse(
        String message,
        UUID id,
        UUID clientId,
        String name,
        String email,
        String phone_number
) {
}
