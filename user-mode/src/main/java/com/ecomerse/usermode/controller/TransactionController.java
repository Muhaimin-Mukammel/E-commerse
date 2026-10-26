package com.ecomerse.usermode.controller;

import com.ecomerse.usermode.dto.transactiondto.*;
import com.ecomerse.usermode.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    private static final UUID TEST_USER_ID = UUID.fromString("68425107-0bc7-42ce-a858-d64038a4f63e");

    @PostMapping("/initiate")
    public ResponseEntity<InitiateTransactionResponse> initiateTransaction(@Valid @RequestBody InitiateTransactionRequest request,
                                                                           @AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(jwt.getSubject());
        InitiateTransactionResponse response = transactionService.initiateTransaction(clientId, request.order_id(), request.method());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/complete")
    public ResponseEntity<CompleteTransactionResponse> completeTransaction(@Valid @RequestBody CompleteTransactionRequest request,
                                                                           @AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(jwt.getSubject());
        CompleteTransactionResponse response = transactionService.completeTransaction(clientId, request.transaction_id(), request.order_id());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancel")
    public ResponseEntity<CancelTransactionResponse> cancelTransaction(@Valid @RequestBody CancelTransactionRequest request,
                                                                       @AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(jwt.getSubject());
        CancelTransactionResponse response = transactionService.cancelTransaction(clientId, request.transaction_id(), request.order_id());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getTransaction")
    public ResponseEntity<GetTransactionResponse> getTransaction(@Valid @RequestBody GetTransactionRequest request,
                                                                 @AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(jwt.getSubject());
        GetTransactionResponse response = transactionService.getTransaction(clientId, request.transaction_id(), request.order_id());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/getTransactions")
    public ResponseEntity<List<GetTransactionResponse>> getTransactionsByOrder(@Valid @RequestBody GetTransactionRequest request,
                                                                               @AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(jwt.getSubject());
        List<GetTransactionResponse> response = transactionService.getTransactionsByOrder(clientId, request.order_id());
        return ResponseEntity.ok(response);
    }
}
