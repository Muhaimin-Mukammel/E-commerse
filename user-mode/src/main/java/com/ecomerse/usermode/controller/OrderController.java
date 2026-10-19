package com.ecomerse.usermode.controller;

import com.ecomerse.usermode.dto.orderdto.*;
import com.ecomerse.usermode.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    private static final UUID TEST_USER_ID = UUID.fromString("68425107-0bc7-42ce-a858-d64038a4f63e");
    private static final UUID TEST_PRODUCT_ID = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");

    @PostMapping("/create")
    public ResponseEntity<CreateOrderResponse> createOrder() {
        CreateOrderResponse response = orderService.createOrder(TEST_USER_ID);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/confirm")
    public ResponseEntity<ConfirmOrderResponse> confirmOrder(@Valid @RequestBody ConfirmOrderRequest request) {
        ConfirmOrderResponse response = orderService.confirmOrder(TEST_USER_ID, request.order_id());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancel")
    public ResponseEntity<CancelOrderResponse> cancelOrder(@Valid @RequestBody CancelOrderRequest request) {
        CancelOrderResponse response = orderService.cancelOrder(TEST_USER_ID, request.order_id());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addItem")
    public ResponseEntity<AddItemToOrderResponse> addItemToOrder(@Valid @RequestBody AddItemToOrderRequest request) {
        AddItemToOrderResponse response = orderService.addItemToOrder(TEST_USER_ID, request.order_id(), request.product_id(), request.quantity());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/removeItem")
    public ResponseEntity<RemoveItemFromOrderResponse> removeItemFromOrder(@Valid @RequestBody RemoveItemFromOrderRequest request) {
        RemoveItemFromOrderResponse response = orderService.removeItemFromOrder(TEST_USER_ID, request.order_id(), request.product_id(), request.quantity());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ClearOrderResponse> clearOrder(@Valid @RequestBody ClearOrderRequest request) {
        ClearOrderResponse response = orderService.clearOrder(TEST_USER_ID, request.order_id());
        return ResponseEntity.ok(response);
    }
}
