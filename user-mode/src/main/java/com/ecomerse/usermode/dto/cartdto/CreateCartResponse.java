package com.ecomerse.usermode.dto.cartdto;

import com.ecomerse.usermode.status.CartStatus;

import java.time.Instant;

public record CreateCartResponse(
        String message,
        Long cartId,
        CartStatus status,
        Instant createAt
) {}
