package com.ecomerse.usermode.dto.transactiondto;

import com.ecomerse.usermode.status.TransactionMethod;
import com.ecomerse.usermode.status.TransactionStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record GetTransactionResponse(
        Long transaction_id,
        UUID order_id,
        TransactionMethod method,
        BigDecimal amount,
        TransactionStatus status,
        Instant created_at,
        Instant completed_at
) {
}
