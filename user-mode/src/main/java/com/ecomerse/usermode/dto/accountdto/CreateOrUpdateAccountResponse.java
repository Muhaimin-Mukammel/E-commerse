package com.ecomerse.usermode.dto.accountdto;

public record CreateOrUpdateAccountResponse(
        String message,
        Long id,
        String keycloakId,
        String name,
        String email,
        String phone_number
) {
}
