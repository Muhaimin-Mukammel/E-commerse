package com.ecomerse.usermode.dto.transactiondto;

import com.ecomerse.usermode.status.TransactionStatus;

import java.util.UUID;

public record CancelTransactionResponse(
        String msg,
        Long transaction_id,
        UUID order_id,
        TransactionStatus status
) {
}
