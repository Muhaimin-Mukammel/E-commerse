package com.ecomerse.usermode.dto.orderdto;

import com.ecomerse.usermode.status.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CreateOrderResponse(
        String message,
        UUID orderId,
        OrderStatus status,
        BigDecimal value,
        Instant createdAt
) {
}
