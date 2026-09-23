package com.ecomerse.usermode.service.Impl;

import com.ecomerse.usermode.dto.accountdto.CreateOrUpdateAccountRequest;
import com.ecomerse.usermode.dto.accountdto.CreateOrUpdateAccountResponse;
import com.ecomerse.usermode.dto.accountdto.DeleteAccountResponse;
import com.ecomerse.usermode.dto.accountdto.ViewAccountResponse;
import com.ecomerse.usermode.entity.UserAccount;
import com.ecomerse.usermode.repository.AccountRepository;
import com.ecomerse.usermode.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public CreateOrUpdateAccountResponse createOrUpdate(CreateOrUpdateAccountRequest request, String keycloakId, String email, String name) {
        Optional<UserAccount> optionalUserAccount = accountRepository.findByKeycloakId(keycloakId);
        UserAccount account;
        if(optionalUserAccount.isPresent()){
            UserAccount account1 = optionalUserAccount.get();

            account1.setName(name);
            account1.setEmail(email);
            account1.setPhone_number(request.phone_number());

            account = account1;
        } else {
            account = new UserAccount(keycloakId, name, email, request.phone_number());
        }
        accountRepository.save(account);
        return new CreateOrUpdateAccountResponse();
    }

    @Override
    public ViewAccountResponse getAccountByKeycloakId(String keycloakId) {
        Optional<UserAccount> account = accountRepository.findByKeycloakId(keycloakId);
        return new ViewAccountResponse();
    }

    @Override
    public DeleteAccountResponse deleteAccount(String keycloakId) {
        Optional<UserAccount> account = accountRepository.findByKeycloakId(keycloakId);
        if(!account.isPresent()){
            throw new RuntimeException();
        }

        accountRepository.deleteByKeycloakId(keycloakId);

        return new DeleteAccountResponse();
    }
}
