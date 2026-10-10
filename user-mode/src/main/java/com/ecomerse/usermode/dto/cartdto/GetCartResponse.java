package com.ecomerse.usermode.dto.cartdto;

import com.ecomerse.usermode.status.CartStatus;
import com.ecomerse.usermode.status.ProductStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record GetCartResponse(
        String message,
        Long cartId,
        CartStatus status,
        Instant createAt,
        Instant updatedAt,
        List<CartItemResponse> items,
        BigDecimal totalValue,
        String currency
) {
    public record CartItemResponse(
            UUID productId,
            String productName,
            int quantity,
            ProductStatus productStatus,
            BigDecimal unitPrice,
            BigDecimal productTotalPrice
    ) {
        public CartItemResponse {
            productTotalPrice = unitPrice().multiply(BigDecimal.valueOf(quantity()));
        }
    }
}
