package com.ecomerse.usermode.dto.orderdto;

import java.util.UUID;

public record RemoveItemFromOrderRequest(
        UUID order_id,
        UUID product_id,
        int quantity
) {
}
