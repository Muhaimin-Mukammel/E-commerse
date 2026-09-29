package com.ecomerse.usermode.dto.accountdto;

import jakarta.validation.constraints.NotNull;

public record CreateOrUpdateAccountRequest(
        @NotNull
        String phone_number
) {
}
