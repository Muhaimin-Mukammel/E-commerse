package com.ecomerse.usermode.controller;

import com.ecomerse.usermode.dto.orderdto.*;
import com.ecomerse.usermode.service.OrderService;
import jakarta.validation.Valid;
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

    @PostMapping("/create")
    public ResponseEntity<CreateOrderResponse> createOrder(@AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(jwt.getSubject());
        CreateOrderResponse response = orderService.createOrder(clientId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/confirm")
    public ResponseEntity<ConfirmOrderResponse> confirmOrder(@Valid @RequestBody ConfirmOrderRequest request,
                                                             @AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(jwt.getSubject());
        ConfirmOrderResponse response = orderService.confirmOrder(clientId, request.order_id());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancel")
    public ResponseEntity<CancelOrderResponse> cancelOrder(@Valid @RequestBody CancelOrderRequest request,
                                                           @AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(jwt.getSubject());
        CancelOrderResponse response = orderService.cancelOrder(clientId, request.order_id());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addItem")
    public ResponseEntity<AddItemToOrderResponse> addItemToOrder(@Valid @RequestBody AddItemToOrderRequest request,
                                                                 @AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(jwt.getSubject());
        AddItemToOrderResponse response = orderService.addItemToOrder(clientId, request.order_id(), request.product_id(), request.quantity());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/removeItem")
    public ResponseEntity<RemoveItemFromOrderResponse> removeItemFromOrder(@Valid @RequestBody RemoveItemFromOrderRequest request,
                                                                           @AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(jwt.getSubject());
        RemoveItemFromOrderResponse response = orderService.removeItemFromOrder(clientId, request.order_id(), request.product_id(), request.quantity());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ClearOrderResponse> clearOrder(@Valid @RequestBody ClearOrderRequest request,
                                                         @AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(jwt.getSubject());
        ClearOrderResponse response = orderService.clearOrder(clientId, request.order_id());
        return ResponseEntity.ok(response);
    }
}
