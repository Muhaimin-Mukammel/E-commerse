package com.ecomerse.usermode.dto.orderdto;

import java.math.BigDecimal;
import java.util.UUID;

public record ClearOrderResponse(
        String msg,
        UUID order_id,
        BigDecimal order_value
) {
}
