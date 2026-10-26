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

import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    private final String TEST_USER_ID = "68425107-0bc7-42ce-a858-d64038a4f63e";

    @PostMapping("/createOrUpdate")
    public ResponseEntity<CreateOrUpdateAccountResponse> createOrUpdateAccount(@Valid @RequestBody CreateOrUpdateAccountRequest request,
                                                                               @AuthenticationPrincipal Jwt jwt){
        UUID clientId = UUID.fromString(jwt.getSubject());
        String email = jwt.getClaims().get("email").toString();
        String name = jwt.getClaims().get("name").toString();

        CreateOrUpdateAccountResponse response = accountService.createOrUpdate(request, clientId, email, name);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/view")
    public ResponseEntity<ViewAccountResponse> viewAccount(@AuthenticationPrincipal Jwt jwt){
        UUID cliendId = UUID.fromString(jwt.getSubject());
        ViewAccountResponse response = accountService.getAccountByClientId(cliendId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteAccountResponse> deleteAccount(@AuthenticationPrincipal Jwt jwt){
        UUID cliendId = UUID.fromString(jwt.getSubject());
        DeleteAccountResponse response = accountService.deleteAccountByClientId(cliendId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
