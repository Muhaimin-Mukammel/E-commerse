package com.ecomerse.usermode.service.Impl;

import com.ecomerse.usermode.dto.transactiondto.CancelTransactionResponse;
import com.ecomerse.usermode.dto.transactiondto.CompleteTransactionResponse;
import com.ecomerse.usermode.dto.transactiondto.GetTransactionResponse;
import com.ecomerse.usermode.dto.transactiondto.InitiateTransactionResponse;
import com.ecomerse.usermode.entity.Order;
import com.ecomerse.usermode.entity.Transaction;
import com.ecomerse.usermode.repository.OrderRepository;
import com.ecomerse.usermode.repository.TransactionRepository;
import com.ecomerse.usermode.service.TransactionService;
import com.ecomerse.usermode.status.OrderStatus;
import com.ecomerse.usermode.status.TransactionMethod;
import com.ecomerse.usermode.status.TransactionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private OrderRepository orderRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, OrderRepository orderRepository) {
        this.transactionRepository = transactionRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public InitiateTransactionResponse initiateTransaction(UUID user_id, UUID order_id, TransactionMethod transactionMethod) {
        Order order = orderRepository.findById(order_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if(!order.getUser_id().equals(user_id)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Order dose not belong to the user");
        }
        if(order.getOrderStatus() != OrderStatus.PENDING_PAYMENT){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order status is not PENDING_PAYMENT");
        }

        boolean hasPending = transactionRepository.existsByOrderIdAndStatus(order_id, TransactionStatus.PENDING);

        if(hasPending){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A pending transaction for this order is already in progress");
        }

        Transaction transaction = new Transaction(order.getOrder_id(), order.getValue(), transactionMethod);
        transactionRepository.save(transaction);

        return new InitiateTransactionResponse("Transaction Initiated Successfully", order_id, transaction.getTransaction_id(), transactionMethod, transaction.getAmount(), transaction.getTransaction_status());
    }

    private Random random = new Random();

    @Override
    @Transactional
    public CompleteTransactionResponse completeTransaction(UUID user_id, Long transaction_id, UUID order_id) {
        Transaction transaction = transactionRepository.findByTransaction_idAndOrder_id(transaction_id, order_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));

        Order order = orderRepository.findById(order_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if(!order.getUser_id().equals(user_id)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Order dose not belong to the user");
        }

        if(order.getOrderStatus() != OrderStatus.PENDING_PAYMENT){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only PENDING_PAYMENT can be completed");
        }

        boolean success = random.nextInt(100) < 85;
        if(success){
            transaction.setTransaction_status(TransactionStatus.SUCCESS);
            transaction.setCompleted_at(Instant.now());
            order.setOrderStatus(OrderStatus.PAID);
            orderRepository.save(order);
        } else {
            transaction.setTransaction_status(TransactionStatus.FAILED);
            transaction.setCompleted_at(Instant.now());
            order.setOrderStatus(OrderStatus.PAYMENT_FAILED);
            orderRepository.save(order);
        }

        transactionRepository.save(transaction);

        return new CompleteTransactionResponse("Transaction Completed Successfully", transaction_id, order_id, transaction.getAmount(), transaction.getTransaction_status(), transaction.getCompleted_at());
    }

    @Override
    @Transactional
    public CancelTransactionResponse cancelTransaction(UUID user_id, Long transaction_id, UUID order_id) {
        Transaction transaction = transactionRepository.findByTransaction_idAndOrder_id(transaction_id, order_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));

        Order order = orderRepository.findById(order_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if(!order.getUser_id().equals(user_id)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Order dose not belong to the user");
        }

        if (transaction.getTransaction_status() != TransactionStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only PENDING transactions can be cancelled");
        }

        transaction.setTransaction_status(TransactionStatus.CANCELED);
        transaction.setCompleted_at(Instant.now());
        order.setOrderStatus(OrderStatus.PAYMENT_CANCELED);
        orderRepository.save(order);
        transactionRepository.save(transaction);

        return new CancelTransactionResponse("Transaction Cancelled", transaction_id, order_id, transaction.getTransaction_status());
    }

    @Override
    @Transactional(readOnly = true)
    public GetTransactionResponse getTransaction(UUID user_id, Long transaction_id, UUID order_id) {
        Transaction transaction = transactionRepository.findByTransaction_idAndOrder_id(transaction_id, order_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));

        Order order = orderRepository.findById(order_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if(!order.getUser_id().equals(user_id)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ACCESS DENIED!! Order dose not belong to the user");
        }

        return new GetTransactionResponse(transaction_id, order_id, transaction.getTransactio_method(), transaction.getAmount(), transaction.getTransaction_status(), transaction.getCreated_at(), transaction.getCompleted_at());
    }

    @Override
    @Transactional
    public List<GetTransactionResponse> getTransactionsByOrder(UUID user_id, UUID order_id) {
        Order order = orderRepository.findById(order_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if(!order.getUser_id().equals(user_id)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ACCESS DENIED!! Order dose not belong to the user");
        }

        return transactionRepository.findByOrder_id(order_id).stream().map(item -> new GetTransactionResponse(item.getTransaction_id(), order_id, item.getTransactio_method(), item.getAmount(), item.getTransaction_status(), item.getCreated_at(), item.getCompleted_at())).toList();
    }
}
