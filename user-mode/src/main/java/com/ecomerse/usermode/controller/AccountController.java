package com.ecomerse.usermode.controller;

import com.ecomerse.usermode.dto.accountdto.CreateOrUpdateAccountRequest;
import com.ecomerse.usermode.dto.accountdto.CreateOrUpdateAccountResponse;
import com.ecomerse.usermode.dto.accountdto.DeleteAccountResponse;
import com.ecomerse.usermode.dto.accountdto.ViewAccountResponse;
import com.ecomerse.usermode.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/createOrUpdate")
    public ResponseEntity<CreateOrUpdateAccountResponse> createOrUpdateAccount(@Valid @RequestBody CreateOrUpdateAccountRequest request, @AuthenticationPrincipal Jwt jwt){
        String keycloakId = jwt.getSubject();
        String email = jwt.getClaimAsString("email");
        String name = jwt.getClaimAsString("name");

        CreateOrUpdateAccountResponse response = accountService.createOrUpdate(request, keycloakId, email, name);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/view/")
    public ResponseEntity<ViewAccountResponse> viewAccount(@AuthenticationPrincipal Jwt jwt){
        ViewAccountResponse response = accountService.getAccountByKeycloakId(jwt.getSubject());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteAccountResponse> deleteAccount(@AuthenticationPrincipal Jwt jwt){
        DeleteAccountResponse response = accountService.deleteAccount(jwt.getSubject());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    /*
    1. Change Password
    3. Get Order Histry
     */
}
