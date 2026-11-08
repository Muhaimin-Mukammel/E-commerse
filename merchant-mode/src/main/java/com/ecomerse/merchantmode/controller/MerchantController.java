package com.ecomerse.merchantmode.controller;

import com.ecomerse.merchantmode.dto.merchantdto.CreateAccountRequest;
import com.ecomerse.merchantmode.dto.merchantdto.CreateAccountResponse;
import com.ecomerse.merchantmode.service.MerchantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class MerchantController {

    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    public ResponseEntity<CreateAccountResponse> createAccount(@AuthenticationPrincipal Jwt jwt,
                                                               @Valid @RequestBody CreateAccountRequest request){
        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        String email = jwt.getClaimAsString("email");
        String name = jwt.getClaimAsString("name");
        CreateAccountResponse response = merchantService.createAccount(clientId, name, email, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
