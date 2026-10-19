package com.ecomerse.usermode.controller;

import com.ecomerse.usermode.dto.accountdto.CreateOrUpdateAccountRequest;
import com.ecomerse.usermode.dto.accountdto.CreateOrUpdateAccountResponse;
import com.ecomerse.usermode.dto.accountdto.DeleteAccountResponse;
import com.ecomerse.usermode.dto.accountdto.ViewAccountResponse;
import com.ecomerse.usermode.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    private final String TEST_USER_ID = "68425107-0bc7-42ce-a858-d64038a4f63e";

    @PostMapping("/createOrUpdate")
    public ResponseEntity<CreateOrUpdateAccountResponse> createOrUpdateAccount(@Valid @RequestBody CreateOrUpdateAccountRequest request){
        String keycloakId = TEST_USER_ID;
        String email = "testemail@gmail.com";
        String name = "testname";

        CreateOrUpdateAccountResponse response = accountService.createOrUpdate(request, keycloakId, email, name);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/view")
    public ResponseEntity<ViewAccountResponse> viewAccount(){
        ViewAccountResponse response = accountService.getAccountByKeycloakId(TEST_USER_ID);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteAccountResponse> deleteAccount(){
        DeleteAccountResponse response = accountService.deleteAccount(TEST_USER_ID);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    /*
    1. Change Password
    3. Get Order Histry
     */
}
