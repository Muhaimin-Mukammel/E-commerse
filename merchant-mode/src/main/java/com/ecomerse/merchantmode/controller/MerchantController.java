package com.ecomerse.merchantmode.controller;

import com.ecomerse.merchantmode.dto.merchantdto.CreateAccountRequest;
import com.ecomerse.merchantmode.dto.merchantdto.CreateAccountResponse;
import com.ecomerse.merchantmode.dto.merchantdto.DeleteAccountResponse;
import com.ecomerse.merchantmode.service.MerchantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class MerchantController {

    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateAccountResponse> createAccount(@AuthenticationPrincipal Jwt jwt,
                                                               @Valid @RequestBody CreateAccountRequest request){
        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        String email = jwt.getClaimAsString("email");
        String name = jwt.getClaimAsString("name");
        CreateAccountResponse response = merchantService.createAccount(clientId, name, email, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<CreateAccountResponse> getMyAccount(@AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        return ResponseEntity.ok(merchantService.getAccountByClientId(clientId));
    }

    @GetMapping("/{merchantId}")
    public ResponseEntity<CreateAccountResponse> getAccountById(@PathVariable UUID merchantId) {
        return ResponseEntity.ok(merchantService.getAccountById(merchantId));
    }

    @PutMapping("/update")
    public ResponseEntity<CreateAccountResponse> updateAccount(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateAccountRequest request) {

        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        String email = jwt.getClaimAsString("email");
        String name = jwt.getClaimAsString("name");

        return ResponseEntity.ok(merchantService.updateAccount(clientId, name, email, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteAccountResponse> deleteAccount(@AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        merchantService.deleteAccount(clientId);
        return ResponseEntity.noContent().build();
    }
}
