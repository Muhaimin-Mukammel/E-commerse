package com.ecomerse.usermode.dto.orderdto;

import com.ecomerse.usermode.status.OrderStatus;

import java.util.UUID;

public record CancelOrderResponse(
        String msg,
        UUID order_id,
        OrderStatus status
) {
}
