package com.ecomerse.usermode.controller;

import com.ecomerse.usermode.dto.orderdto.*;
import com.ecomerse.usermode.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    public ResponseEntity<CreateOrderResponse> createOrder(@AuthenticationPrincipal Jwt jwt) {
        CreateOrderResponse response = orderService.createOrder(TEST_USER_ID);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/confirm")
    public ResponseEntity<ConfirmOrderResponse> confirmOrder(@RequestParam ConfirmOrderRequest request, @AuthenticationPrincipal Jwt jwt) {
        ConfirmOrderResponse response = orderService.confirmOrder(TEST_USER_ID, request.order_id());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancel")
    public ResponseEntity<CancelOrderResponse> cancelOrder(@RequestParam CancelOrderRequest request, @AuthenticationPrincipal Jwt jwt) {
        CancelOrderResponse response = orderService.cancelOrder(TEST_USER_ID, request.order_id());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addItem")
    public ResponseEntity<AddItemToOrderResponse> addItemToOrder(@RequestParam AddItemToOrderRequest request, @AuthenticationPrincipal Jwt jwt) {
        AddItemToOrderResponse response = orderService.addItemToOrder(TEST_USER_ID, request.order_id(), request.product_id(), request.quantity());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/removeItem")
    public ResponseEntity<RemoveItemFromOrderResponse> removeItemFromOrder(@RequestParam RemoveItemFromOrderRequest request, @AuthenticationPrincipal Jwt jwt) {
        RemoveItemFromOrderResponse response = orderService.removeItemFromOrder(TEST_USER_ID, request.order_id(), request.product_id(), request.quantity());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ClearOrderResponse> clearOrder(@RequestParam ClearOrderRequest request, @AuthenticationPrincipal Jwt jwt) {
        ClearOrderResponse response = orderService.clearOrder(TEST_USER_ID, request.order_id());
        return ResponseEntity.ok(response);
    }
}
