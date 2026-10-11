package com.ecomerse.usermode.dto.cartdto;

import java.util.UUID;

public record AddItemToCartRequest(
        UUID product_id,
        Integer quantity
) {
}
