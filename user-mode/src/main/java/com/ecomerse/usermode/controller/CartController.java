package com.ecomerse.usermode.controller;

import com.ecomerse.usermode.dto.cartdto.*;
import com.ecomerse.usermode.service.CartService;
import jakarta.validation.Valid;
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

    private static final UUID TEST_USER_ID = UUID.fromString("68425107-0bc7-42ce-a858-d64038a4f63e");
    private static final UUID TEST_PRODUCT_ID = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");

  @PostMapping("/create")
  public ResponseEntity<CreateCartResponse> createCart(@AuthenticationPrincipal Jwt jwt) {
      CreateCartResponse response = cartService.createCart(TEST_USER_ID);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/getCart")
  public ResponseEntity<GetCartResponse> getCart(@AuthenticationPrincipal Jwt jwt){
      GetCartResponse response = cartService.getCartByCartId(TEST_USER_ID);
      return ResponseEntity.ok(response);
  }

  @PostMapping("/addItem")
  public ResponseEntity<AddItemToCartResponse> addItemToCart(
          @Valid @RequestBody AddItemToCartRequest request, @AuthenticationPrincipal Jwt jwt){
      AddItemToCartResponse response = cartService.addItemToCart(TEST_USER_ID, TEST_PRODUCT_ID, request.quantity());
      return ResponseEntity.ok(response);
  }

    @PatchMapping("/removeItem")
    public ResponseEntity<RemoveItemFromCartResponse> removeItemFromCart(
            @Valid @RequestBody RemoveItemFromCartRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        RemoveItemFromCartResponse response = cartService.removeItemFromCart(TEST_USER_ID, TEST_PRODUCT_ID, request.quantity());
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/clear")
  public ResponseEntity<ClearCartResponse> clearCart(@AuthenticationPrincipal Jwt jwt){
        ClearCartResponse response = cartService.clearCart(TEST_USER_ID);
        return ResponseEntity.ok(response);
  }
}
