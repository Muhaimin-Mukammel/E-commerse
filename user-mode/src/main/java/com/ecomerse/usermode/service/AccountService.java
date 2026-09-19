package com.ecomerse.usermode.service;

import com.ecomerse.usermode.dto.accountdto.CreateOrUpdateAccountRequest;
import com.ecomerse.usermode.dto.accountdto.CreateOrUpdateAccountResponse;
import com.ecomerse.usermode.dto.accountdto.DeleteAccountResponse;
import com.ecomerse.usermode.dto.accountdto.ViewAccountResponse;
import jakarta.validation.Valid;

public interface AccountService {
    CreateOrUpdateAccountResponse createOrUpdate(@Valid CreateOrUpdateAccountRequest request, String id, String email, String name);
    ViewAccountResponse getAccountByKeycloakId(String id);

    DeleteAccountResponse deleteAccount(String keycloakId);
}
