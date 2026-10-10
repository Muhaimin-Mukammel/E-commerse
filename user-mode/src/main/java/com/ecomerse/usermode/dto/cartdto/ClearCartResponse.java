package com.ecomerse.usermode.dto.cartdto;

import java.math.BigDecimal;

public record ClearCartResponse(
        String msg,
        Long cartId,
        BigDecimal total_value
) {
}
