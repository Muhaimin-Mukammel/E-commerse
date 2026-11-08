package com.ecomerse.merchantmode.service.Impl;

import com.ecomerse.merchantmode.dto.merchantdto.CreateAccountRequest;
import com.ecomerse.merchantmode.dto.merchantdto.CreateAccountResponse;
import com.ecomerse.merchantmode.dto.merchantdto.DeleteAccountResponse;
import com.ecomerse.merchantmode.entity.Merchant;
import com.ecomerse.merchantmode.entity.Store;
import com.ecomerse.merchantmode.entity.StoreAddress;
import com.ecomerse.merchantmode.entity.StoreContact;
import com.ecomerse.merchantmode.repository.MerchantRepository;
import com.ecomerse.merchantmode.service.MerchantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;

    public MerchantServiceImpl(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    @Transactional
    public CreateAccountResponse createAccount(UUID clientId, String name, String email, @Valid @RequestBody CreateAccountRequest request) {
        Optional<Merchant> merchant = merchantRepository.findByClientId(clientId);

        if(merchant.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client already has a account");
        }

        Merchant newMerchant = new Merchant();
        setMerchantValues(newMerchant, request);
        return mapToCreateAccountResponse(newMerchant);
    }

    @Override
    @Transactional
    public CreateAccountResponse getAccountByClientId(UUID clientId) {
        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found. Client Id : " + clientId));
        return mapToCreateAccountResponse(merchant);
    }

    @Override
    @Transactional
    public CreateAccountResponse getAccountById(UUID merchantId) {
        Merchant merhcant = merchantRepository.findByMerchantId(merchantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found. Merchant Id : " + merchantId));

        return mapToCreateAccountResponse(merhcant);
    }

    @Override
    @Transactional
    public CreateAccountResponse updateAccount(UUID clientId, String name, String email, CreateAccountRequest request) {
        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found. Client Id : " + clientId));
        setMerchantValues(merchant, request);
        return mapToCreateAccountResponse(merchant);
    }

    @Override
    @Transactional
    public DeleteAccountResponse deleteAccount(UUID clientId) {
        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found. Client Id : " + clientId));

        merchantRepository.delete(merchant);

        return new DeleteAccountResponse("Account has been deleted");
    }

    private CreateAccountResponse mapToCreateAccountResponse(Merchant merchant) {
        Store store = merchant.getStore();

        return new CreateAccountResponse(
                merchant.getMerchantId(),
                merchant.getMerchantName(),
                merchant.getMerchantEmail(),
                merchant.getCreatedAt(),
                merchant.getUpdatedAt(),
                new CreateAccountResponse.StoreInfo(
                        store.getStoreId(),
                        store.getPublicStoreId(),
                        store.getStoreName(),
                        new CreateAccountResponse.StoreContactInfo(
                                store.getStoreContact().getStorePhone(),
                                store.getStoreContact().getStoreEmail(),
                                store.getStoreContact().getStoreWebsiteLink()
                        ),
                        new CreateAccountResponse.StoreAddressInfo(
                                store.getStoreAddress().getStoreCountry(),
                                store.getStoreAddress().getStoreCity(),
                                store.getStoreAddress().getStoreRegion(),
                                store.getStoreAddress().getStorePostalCode()
                        )
                )
        );
    }
    private void setMerchantValues(Merchant merchant, CreateAccountRequest request) {
        Store store = merchant.getStore();
        if (store == null) {
            store = new Store();
            store.setMerchant(merchant);
            merchant.setStore(store);
        }

        if (request.storeName() != null) {
            store.setStoreName(request.storeName());
        }

        StoreContact contact = store.getStoreContact();
        if (contact == null) {
            contact = new StoreContact();
            contact.setStore(store);
            store.setStoreContact(contact);
        }

        if (request.storePhone() != null) {
            contact.setStorePhone(request.storePhone());
        }
        if (request.storeEmail() != null) {
            contact.setStoreEmail(request.storeEmail());
        }
        if (request.storeWebLink() != null) {
            contact.setStoreWebsiteLink(request.storeWebLink());
        }

        StoreAddress address = store.getStoreAddress();
        if (address == null) {
            address = new StoreAddress();
            address.setStore(store);
            store.setStoreAddress(address);
        }

        if (request.storeCountry() != null) {
            address.setStoreCountry(request.storeCountry());
        }
        if (request.storeCity() != null) {
            address.setStoreCity(request.storeCity());
        }
        if (request.storeRegion() != null) {
            address.setStoreRegion(request.storeRegion());
        }
        if (request.storePostalCode() != null) {
            address.setStorePostalCode(request.storePostalCode());
        }
    }
}
