package com.ecomerse.merchantmode.service.Impl;

import com.ecomerse.merchantmode.dto.merchantdto.CreateAccountRequest;
import com.ecomerse.merchantmode.dto.merchantdto.CreateAccountResponse;
import com.ecomerse.merchantmode.entity.Merchant;
import com.ecomerse.merchantmode.entity.Store;
import com.ecomerse.merchantmode.entity.StoreAddress;
import com.ecomerse.merchantmode.entity.StoreContact;
import com.ecomerse.merchantmode.repository.MerchantRepository;
import com.ecomerse.merchantmode.service.MerchantService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
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
        Optional<Merchant> optionalmerchant = merchantRepository.findByClientId(clientId);
        Merchant merchant1;
        if (optionalmerchant.isPresent()) {
            Merchant merchant = optionalmerchant.get();

            merchant.setMerchantName(name);
            merchant.setMerchantEmail(email);

            Store merchantStore = merchant.getStore();
            merchantStore.setStoreName(request.storeName());
            merchantStore.setUpdatedAt(Instant.now());

            StoreContact storeContact = merchantStore.getStoreContact();
            storeContact.setStoreEmail(request.storeEmail());
            storeContact.setStorePhone(request.storePhone());
            storeContact.setStoreWebsiteLink(request.storeWebLink());

            StoreAddress storeAddress = merchantStore.getStoreAddress();
            storeAddress.setStoreCountry(request.storeCountry());
            storeAddress.setStoreCity(request.storeCity());
            storeAddress.setStoreRegion(request.storeRegion());
            storeAddress.setStorePostalCode(request.storePostalCode());

            merchant1 = merchant;
        }
        else {
            Merchant merchant = new Merchant();
            merchant.setMerchantName(name);
            merchant.setMerchantEmail(email);
            merchant.setCreatedAt(Instant.now());
            merchant.setUpdatedAt(Instant.now());

            Store store = new Store();
            store.setStoreName(request.storeName());
            store.setCreatedAt(Instant.now());
            store.setUpdatedAt(Instant.now());
            store.setMerchant(merchant);

            StoreContact storeContact = new StoreContact();
            storeContact.setStoreEmail(request.storeEmail());
            storeContact.setStorePhone(request.storePhone());
            storeContact.setStoreWebsiteLink(request.storeWebLink());
            storeContact.setStore(store);

            StoreAddress storeAddress = new StoreAddress();
            storeAddress.setStoreCountry(request.storeCountry());
            storeAddress.setStoreCity(request.storeCity());
            storeAddress.setStoreRegion(request.storeRegion());
            storeAddress.setStorePostalCode(request.storePostalCode());
            storeAddress.setStore(store);

            store.setStoreContact(storeContact);
            store.setStoreAddress(storeAddress);
            merchant.setStore(store);

            merchant1 = merchant;
        }
        merchantRepository.save(merchant1);
        return new CreateAccountResponse(merchant1.getMerchantId(), merchant1.getMerchantName(), merchant1.getMerchantEmail(), merchant1.getCreatedAt(), merchant1.getUpdatedAt(),
                new CreateAccountResponse.StoreInfo(merchant1.getStore().getStoreId(), merchant1.getStore().getPublicStoreId(), merchant1.getStore().getStoreName(), new CreateAccountResponse.StoreContactInfo(
                        merchant1.getStore().getStoreContact().getStorePhone(), merchant1.getStore().getStoreContact().getStoreEmail(), merchant1.getStore().getStoreContact().getStoreWebsiteLink()),
                        new CreateAccountResponse.StoreAddressInfo(merchant1.getStore().getStoreAddress().getStoreCountry(), merchant1.getStore().getStoreAddress().getStoreCity(), merchant1.getStore().getStoreAddress().getStoreRegion(),
                                merchant1.getStore().getStoreAddress().getStorePostalCode())));
    }
}
