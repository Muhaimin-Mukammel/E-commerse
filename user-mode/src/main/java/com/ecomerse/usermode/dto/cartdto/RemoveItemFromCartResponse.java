package com.ecomerse.usermode.dto.cartdto;

import java.math.BigDecimal;

public record RemoveItemFromCartResponse(
        String msg,
        Long cart_id,
        BigDecimal total_value
) {
}
