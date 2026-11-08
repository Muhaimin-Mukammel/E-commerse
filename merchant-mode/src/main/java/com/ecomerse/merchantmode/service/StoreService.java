package com.ecomerse.merchantmode.service;

import com.ecomerse.merchantmode.dto.storedto.CreateStoreRequest;
import com.ecomerse.merchantmode.dto.storedto.DeleteStoreResponse;
import com.ecomerse.merchantmode.dto.storedto.StoreResponse;
import jakarta.validation.Valid;

import java.util.UUID;

public interface StoreService {
    StoreResponse createStore(UUID clientId, @Valid CreateStoreRequest request);
    StoreResponse getStoreByClientId(UUID clientId);
    StoreResponse getStoreByPublicId(UUID publicStoreId);
    StoreResponse updateStore(UUID clientId, @Valid CreateStoreRequest request);
    DeleteStoreResponse deleteStore(UUID clientId);
}
