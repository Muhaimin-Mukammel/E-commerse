package com.ecomerse.merchantmode.dto.productdto;

import java.math.BigDecimal;

public record CreateProductRequest(
        String productName,
        BigDecimal productPrice,
        String description,
        Integer initialStock
) {
}
