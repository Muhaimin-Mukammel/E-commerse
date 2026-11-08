package com.ecomerse.merchantmode.dto.productdto;

import com.ecomerse.merchantmode.status.ProductStatus;

import java.math.BigDecimal;

public record UpdateProductRequest(
        String productName,
        BigDecimal productPrice,
        String description,
        ProductStatus status
) {
}
