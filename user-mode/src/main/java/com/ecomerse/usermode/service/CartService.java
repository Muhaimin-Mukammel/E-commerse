package com.ecomerse.usermode.service;

import com.ecomerse.usermode.dto.cartdto.*;

import java.util.UUID;

public interface CartService {
    CreateCartResponse createCart(UUID userId);
    GetCartResponse getCartByCartId(UUID user_id);
    AddItemToCartResponse addItemToCart(UUID user_id, UUID item_Id, Integer quantity);
    RemoveItemFromCartResponse removeItemFromCart(UUID user_id, UUID item_id, Integer quantity);
    ClearCartResponse clearCart(UUID user_id);
}
