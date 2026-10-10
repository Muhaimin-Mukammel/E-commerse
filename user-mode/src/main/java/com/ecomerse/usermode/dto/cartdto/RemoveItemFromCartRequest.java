package com.ecomerse.usermode.dto.cartdto;

public record RemoveItemFromCartRequest(
        // UUID product_id,this will be added later when the requied services to add this will be build
        Integer quantity
) {
}
