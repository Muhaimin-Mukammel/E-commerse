package com.ecomerse.usermode.dto.transactiondto;

import com.ecomerse.usermode.status.TransactionMethod;
import com.ecomerse.usermode.status.TransactionStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record InitiateTransactionResponse(
        String msg,
        UUID order_id,
        Long transaction_id,
        TransactionMethod method,
        BigDecimal amount,
        TransactionStatus status
) {
}
