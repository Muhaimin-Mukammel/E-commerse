package com.ecomerse.usermode.dto.orderdto;

import java.util.UUID;

public record CancelOrderRequest(
        UUID order_id
) {
}
