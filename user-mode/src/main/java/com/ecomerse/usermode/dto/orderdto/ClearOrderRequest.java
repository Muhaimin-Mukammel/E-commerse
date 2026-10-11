package com.ecomerse.usermode.dto.orderdto;

import java.util.UUID;

public record ClearOrderRequest(
        UUID order_id
) {
}
