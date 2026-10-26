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

  @PostMapping("/create")
  public ResponseEntity<CreateCartResponse> createCart(@AuthenticationPrincipal Jwt jwt) {
      UUID clientId = UUID.fromString(jwt.getSubject());
      CreateCartResponse response = cartService.createCart(clientId);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/getCart")
  public ResponseEntity<GetCartResponse> getCart(@AuthenticationPrincipal Jwt jwt) {
      UUID clientId = UUID.fromString(jwt.getSubject());
      GetCartResponse response = cartService.getCartByCartId(clientId);
      return ResponseEntity.ok(response);
  }

  @PostMapping("/addItem")
  public ResponseEntity<AddItemToCartResponse> addItemToCart(
          @Valid @RequestBody AddItemToCartRequest request,
          @AuthenticationPrincipal Jwt jwt){
      UUID clientId = UUID.fromString(jwt.getSubject());
      UUID productId = request.product_id();
      AddItemToCartResponse response = cartService.addItemToCart(clientId, productId, request.quantity());
      return ResponseEntity.ok(response);
  }

    @PatchMapping("/removeItem")
    public ResponseEntity<RemoveItemFromCartResponse> removeItemFromCart(
            @Valid @RequestBody RemoveItemFromCartRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(jwt.getSubject());
        UUID productId = request.product_id();
        RemoveItemFromCartResponse response = cartService.removeItemFromCart(clientId, productId, request.quantity());
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/clear")
  public ResponseEntity<ClearCartResponse> clearCart(@AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(jwt.getSubject());
        ClearCartResponse response = cartService.clearCart(clientId);
        return ResponseEntity.ok(response);
  }
}
