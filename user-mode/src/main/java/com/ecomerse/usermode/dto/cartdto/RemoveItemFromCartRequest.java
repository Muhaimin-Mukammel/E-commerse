package com.ecomerse.usermode.dto.cartdto;

import java.util.UUID;

public record RemoveItemFromCartRequest(
        UUID product_id,
        Integer quantity
) {
}
