package com.ecomerse.usermode.dto.orderdto;

import java.math.BigDecimal;
import java.util.UUID;

public record RemoveItemFromOrderResponse(
        String msg,
        UUID order_id,
        BigDecimal order_value
) {
}
