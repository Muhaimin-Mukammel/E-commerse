package com.ecomerse.usermode.dto.orderdto;

import java.util.UUID;

public record ConfirmOrderRequest(
        UUID order_id
) {
}
