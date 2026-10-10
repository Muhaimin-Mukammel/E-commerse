package com.ecomerse.usermode.dto.cartdto;

import java.math.BigDecimal;

public record AddItemToCartResponse(
        String msg,
        Long cart_id,
        BigDecimal total_value
) {
}
