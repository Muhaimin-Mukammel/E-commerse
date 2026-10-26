package com.ecomerse.usermode.service;

import com.ecomerse.usermode.dto.accountdto.CreateOrUpdateAccountRequest;
import com.ecomerse.usermode.dto.accountdto.CreateOrUpdateAccountResponse;
import com.ecomerse.usermode.dto.accountdto.DeleteAccountResponse;
import com.ecomerse.usermode.dto.accountdto.ViewAccountResponse;
import jakarta.validation.Valid;

import java.util.UUID;

public interface AccountService {
    CreateOrUpdateAccountResponse createOrUpdate(@Valid CreateOrUpdateAccountRequest request, UUID userId, String email, String name);
    ViewAccountResponse getAccountByClientId(UUID cliendId);

    DeleteAccountResponse deleteAccountByClientId(UUID clientId);
}
