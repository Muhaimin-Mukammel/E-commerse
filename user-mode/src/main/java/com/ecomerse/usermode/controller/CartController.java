package com.ecomerse.usermode.controller;


import com.ecomerse.usermode.dto.cartdto.CreateCartRequest;
import com.ecomerse.usermode.dto.cartdto.CreateCartResponse;
import com.ecomerse.usermode.dto.cartdto.GetCartResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/cart")
public class CartController {

 @PostMapping("/create")
 public ResponseEntity<CreateCartResponse> createCart(@RequestBody CreateCartRequest request){
   return null;
 }

 @GetMapping("/getCart")
 public ResponseEntity<GetCartResponse> getCart(@AuthenticationPrincipal Jwt jwt){
  return null;
 }

 /*
  1. addItemToCart
  2. editCartt
  3. remoceItemFromCart
  4. ClearCart
 */
}
