package com.ecomerse.usermode.service.Impl;

import com.ecomerse.usermode.dto.accountdto.CreateOrUpdateAccountRequest;
import com.ecomerse.usermode.dto.accountdto.CreateOrUpdateAccountResponse;
import com.ecomerse.usermode.dto.accountdto.DeleteAccountResponse;
import com.ecomerse.usermode.dto.accountdto.ViewAccountResponse;
import com.ecomerse.usermode.entity.UserAccount;
import com.ecomerse.usermode.repository.AccountRepository;
import com.ecomerse.usermode.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public CreateOrUpdateAccountResponse createOrUpdate(CreateOrUpdateAccountRequest request, UUID clientId, String email, String name) {
        Optional<UserAccount> optionalUserAccount = accountRepository.findByClientId(clientId);
        UserAccount account;
        if(optionalUserAccount.isPresent()){
            UserAccount account1 = optionalUserAccount.get();

            account1.setName(name);
            account1.setEmail(email);
            account1.setPhone_number(request.phone_number());

            account = account1;
        } else {
            account = new UserAccount(clientId, name, email, request.phone_number());
        }
        accountRepository.save(account);
        return new CreateOrUpdateAccountResponse(
                "Create/Update complete", account.getUser_id(), account.getClientId(), account.getName(), account.getEmail(), account.getPhone_number()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ViewAccountResponse getAccountByClientId(UUID clientId) {
        UserAccount account = accountRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found. ClinetId : " + clientId));
        return new ViewAccountResponse(
                account.getUser_id(), account.getClientId(), account.getName(), account.getEmail(),account.getPhone_number(), account.isActive(), account.getCreatedAt()
        );
    }

    @Override
    @Transactional
    public DeleteAccountResponse deleteAccountByClientId(UUID clientId) {
        Optional<UserAccount> account = accountRepository.findByClientId(clientId);
        if(!account.isPresent()){
            throw new RuntimeException("Account not found for Client ID: " + clientId);
        }

        accountRepository.deleteByClientId(clientId);

        return new DeleteAccountResponse("Deletion Complete.");
    }
}
