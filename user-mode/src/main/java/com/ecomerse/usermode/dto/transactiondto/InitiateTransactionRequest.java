package com.ecomerse.usermode.dto.transactiondto;

import com.ecomerse.usermode.status.TransactionMethod;

import java.util.UUID;

public record InitiateTransactionRequest(
        // UUID user_id
        UUID order_id,
        TransactionMethod method
) {
}
