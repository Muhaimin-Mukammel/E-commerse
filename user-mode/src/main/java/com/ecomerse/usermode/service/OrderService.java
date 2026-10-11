package com.ecomerse.usermode.service;

import com.ecomerse.usermode.dto.orderdto.*;

import java.util.UUID;

public interface OrderService {
    CreateOrderResponse createOrder(UUID user_id);
    ConfirmOrderResponse confirmOrder(UUID user_id, UUID order_id);
    CancelOrderResponse cancelOrder(UUID user_id, UUID order_id);
    AddItemToOrderResponse addItemToOrder(UUID user_id, UUID order_id, UUID product_id, int quantity);
    RemoveItemFromOrderResponse removeItemFromOrder(UUID user_id, UUID order_id,  UUID product_id, int quantity);
    ClearOrderResponse clearOrder(UUID user_id, UUID order_id);
}
