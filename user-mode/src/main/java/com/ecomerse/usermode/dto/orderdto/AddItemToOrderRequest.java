package com.ecomerse.usermode.dto.orderdto;

import java.util.UUID;

public record AddItemToOrderRequest(
        UUID order_id,
        UUID product_id,
        int quantity
) {
}
