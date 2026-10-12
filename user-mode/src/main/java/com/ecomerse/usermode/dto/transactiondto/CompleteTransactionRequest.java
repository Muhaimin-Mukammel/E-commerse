package com.ecomerse.usermode.dto.transactiondto;

import java.util.UUID;

public record CompleteTransactionRequest(
        // UUID user_id
        UUID order_id,
        Long transaction_id
) {
}
