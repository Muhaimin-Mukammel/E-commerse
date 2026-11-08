package com.ecomerse.merchantmode.service.Impl;

import com.ecomerse.merchantmode.dto.storedto.CreateStoreRequest;
import com.ecomerse.merchantmode.dto.storedto.DeleteStoreResponse;
import com.ecomerse.merchantmode.dto.storedto.StoreResponse;
import com.ecomerse.merchantmode.entity.Merchant;
import com.ecomerse.merchantmode.entity.Store;
import com.ecomerse.merchantmode.entity.StoreAddress;
import com.ecomerse.merchantmode.entity.StoreContact;
import com.ecomerse.merchantmode.repository.MerchantRepository;
import com.ecomerse.merchantmode.repository.StoreRepository;
import com.ecomerse.merchantmode.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class StoreServiceImpl implements StoreService {

    private final MerchantRepository merchantRepository;
    private final StoreRepository storeRepository;

    public StoreServiceImpl(MerchantRepository merchantRepository, StoreRepository storeRepository) {
        this.merchantRepository = merchantRepository;
        this.storeRepository = storeRepository;
    }

    @Override
    @Transactional
    public StoreResponse createStore(UUID clientId, CreateStoreRequest request) {
        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));

        if(merchant.getStore() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Merchant already has a store");
        }
        Store store = new Store();
        merchant.setStore(store);
        setStoreValue(store, request);

        storeRepository.save(store);
        return mapToResponse(store);
    }

    @Override
    @Transactional(readOnly = true)
    public StoreResponse getStoreByClientId(UUID clientId) {
        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));

        if(merchant.getStore() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found");
        }

        return mapToResponse(merchant.getStore());
    }

    @Override
    @Transactional(readOnly = true)
    public StoreResponse getStoreByPublicId(UUID publicStoreId) {
        Store store = storeRepository.findByPublicStoreId(publicStoreId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant dose not have a store"));

        return mapToResponse(store);
    }

    @Override
    @Transactional
    public StoreResponse updateStore(UUID clientId, CreateStoreRequest request) {
        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));

        Store store = merchant.getStore();
        if(merchant.getStore() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant dose not have a store");
        }

        setStoreValue(store, request);
        storeRepository.save(store);
        return mapToResponse(store);
    }

    @Override
    @Transactional
    public DeleteStoreResponse deleteStore(UUID clientId) {

        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));

        Store store = merchant.getStore();
        if(merchant.getStore() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant dose not have a store");
        }

        storeRepository.delete(store);
        return new DeleteStoreResponse("Successfully deleted store");
    }

    private StoreResponse mapToResponse(Store store) {
        return new StoreResponse(
                store.getStoreId(),
                store.getPublicStoreId(),
                store.getStoreName(),
                store.getCreatedAt(),
                store.getUpdatedAt(),
                new StoreResponse.ContactInfo(
                        store.getStoreContact().getStorePhone(),
                        store.getStoreContact().getStoreEmail(),
                        store.getStoreContact().getStoreWebsiteLink()
                ),
                new StoreResponse.AddressInfo(
                        store.getStoreAddress().getStoreCountry(),
                        store.getStoreAddress().getStoreCity(),
                        store.getStoreAddress().getStoreRegion(),
                        store.getStoreAddress().getStorePostalCode()
                )
        );
    }

    private void setStoreValue(Store store, CreateStoreRequest request) {
        if (request.storeName() != null) {
            store.setStoreName(request.storeName());
        }

        StoreContact contact = new StoreContact();
        contact.setStore(store);

        if (request.storePhone() != null) {
            contact.setStorePhone(request.storePhone());
        }
        if (request.storeEmail() != null) {
            contact.setStoreEmail(request.storeEmail());
        }
        if (request.storeWebLink() != null) {
            contact.setStoreWebsiteLink(request.storeWebLink());
        }

        StoreAddress address = new StoreAddress();
        address.setStore(store);

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

        store.setStoreContact(contact);
        store.setStoreAddress(address);
    }
}
