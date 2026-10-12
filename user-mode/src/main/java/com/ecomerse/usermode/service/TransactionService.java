package com.ecomerse.usermode.service;

import com.ecomerse.usermode.dto.transactiondto.CancelTransactionResponse;
import com.ecomerse.usermode.dto.transactiondto.CompleteTransactionResponse;
import com.ecomerse.usermode.dto.transactiondto.GetTransactionResponse;
import com.ecomerse.usermode.dto.transactiondto.InitiateTransactionResponse;
import com.ecomerse.usermode.status.TransactionMethod;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    InitiateTransactionResponse initiateTransaction(UUID user_id, UUID order_id, TransactionMethod transactionMethod);
    CompleteTransactionResponse completeTransaction(UUID user_id, Long transaction_id, UUID order_id);
    CancelTransactionResponse cancelTransaction(UUID user_id, Long transaction_id, UUID order_id);
    GetTransactionResponse getTransaction(UUID user_id, Long transaction_id, UUID order_id);
    List<GetTransactionResponse> getTransactionsByOrder(UUID user_id, UUID order_id);
}
