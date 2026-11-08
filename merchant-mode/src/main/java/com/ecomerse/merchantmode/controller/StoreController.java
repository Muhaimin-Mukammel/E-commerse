package com.ecomerse.merchantmode.controller;

import com.ecomerse.merchantmode.dto.storedto.CreateStoreRequest;
import com.ecomerse.merchantmode.dto.storedto.DeleteStoreResponse;
import com.ecomerse.merchantmode.dto.storedto.StoreResponse;
import com.ecomerse.merchantmode.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<StoreResponse> createStore(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateStoreRequest request) {
        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        StoreResponse response = storeService.createStore(clientId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<StoreResponse> getStore(@AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        StoreResponse response = storeService.getStoreByClientId(clientId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{publicStoreId}")
    public ResponseEntity<StoreResponse> getStoreByPublicId(@PathVariable UUID publicStoreId) {
        StoreResponse response = storeService.getStoreByPublicId(publicStoreId);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<StoreResponse> updateStore(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateStoreRequest request) {
        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        StoreResponse response = storeService.updateStore(clientId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<DeleteStoreResponse> deleteStore(@AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        DeleteStoreResponse response = storeService.deleteStore(clientId);
        return ResponseEntity.ok(response);
    }
}
