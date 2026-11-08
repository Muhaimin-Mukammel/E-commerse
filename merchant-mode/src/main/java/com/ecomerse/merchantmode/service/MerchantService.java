package com.ecomerse.merchantmode.service;

import com.ecomerse.merchantmode.dto.merchantdto.CreateAccountRequest;
import com.ecomerse.merchantmode.dto.merchantdto.CreateAccountResponse;
import com.ecomerse.merchantmode.dto.merchantdto.DeleteAccountResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface MerchantService {
    CreateAccountResponse createAccount(UUID clientId, String name, String email, @Valid @RequestBody CreateAccountRequest request);
    CreateAccountResponse getAccountByClientId(UUID clientId);
    CreateAccountResponse getAccountById(UUID merchantId);
    CreateAccountResponse updateAccount(UUID clientId, String name, String email, CreateAccountRequest request);
    DeleteAccountResponse deleteAccount(UUID clientId);
}
