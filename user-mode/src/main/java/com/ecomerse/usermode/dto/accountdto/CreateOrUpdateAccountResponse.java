package com.ecomerse.usermode.dto.accountdto;

import java.util.UUID;

public record CreateOrUpdateAccountResponse(
        String message,
        UUID id,
        UUID cliendId,
        String name,
        String email,
        String phone_number
) {
}
