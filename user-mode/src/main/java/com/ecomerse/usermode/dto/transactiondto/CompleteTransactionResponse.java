package com.ecomerse.usermode.dto.transactiondto;

import com.ecomerse.usermode.status.TransactionStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CompleteTransactionResponse(
        String msg,
        Long transaction_id,
        UUID order_id,
        BigDecimal amount,
        TransactionStatus status,
        Instant completion_at
) {
}
