package com.ecomerse.usermode.controller;

import com.ecomerse.usermode.dto.cartdto.*;
import com.ecomerse.usermode.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

  private final CartService cartService;

  public CartController(CartService cartService) {
   this.cartService = cartService;
  }

  @PostMapping("/create")
  public ResponseEntity<CreateCartResponse> createCart(@AuthenticationPrincipal Jwt jwt) {
      UUID test_user_id = UUID.fromString("68425107-0bc7-42ce-a858-d64038a4f63e");
      CreateCartResponse response = cartService.createCart(test_user_id);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/getCart")
  public ResponseEntity<GetCartResponse> getCart(@AuthenticationPrincipal Jwt jwt){
      UUID test_user_id = UUID.fromString("68425107-0bc7-42ce-a858-d64038a4f63e");
      GetCartResponse response = cartService.getCartByCartId(test_user_id);
      return ResponseEntity.ok(response);
  }

  @PostMapping("/addItem")
  public ResponseEntity<AddItemToCartResponse> addItemToCart(
          @RequestBody AddItemToCartRequest request,  @AuthenticationPrincipal Jwt jwt){
      UUID test_user_id = UUID.fromString("68425107-0bc7-42ce-a858-d64038a4f63e");
      UUID test_product_id = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");
      AddItemToCartResponse response = cartService.addItemToCart(test_user_id, test_product_id, request.quantity());
      return ResponseEntity.ok(response);
  }

    @PatchMapping("/removeItem")
    public ResponseEntity<RemoveItemFromCartResponse> removeItemFromCart(
            @RequestBody RemoveItemFromCartRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        UUID test_user_id = UUID.fromString("68425107-0bc7-42ce-a858-d64038a4f63e");
        UUID test_product_id = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        RemoveItemFromCartResponse response = cartService.removeItemFromCart(test_user_id, test_product_id, request.quantity());
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/clear")
  public ResponseEntity<ClearCartResponse> clearCart(@AuthenticationPrincipal Jwt jwt){
        UUID test_user_id = UUID.fromString("68425107-0bc7-42ce-a858-d64038a4f63e");
        ClearCartResponse response = cartService.clearCart(test_user_id);
        return ResponseEntity.ok(response);
  }
}
