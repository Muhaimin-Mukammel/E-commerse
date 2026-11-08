package com.ecomerse.merchantmode.controller;

import com.ecomerse.merchantmode.dto.inventorydto.DeleteInventoryResponse;
import com.ecomerse.merchantmode.dto.inventorydto.InventoryResponse;
import com.ecomerse.merchantmode.dto.inventorydto.UpdateStockRequest;
import com.ecomerse.merchantmode.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<InventoryResponse> createInventory(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID productId,
            @Valid @RequestBody UpdateStockRequest request) {

        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        String merchantName = jwt.getClaimAsString("name");
        InventoryResponse response = inventoryService.createInventory(clientId, productId, merchantName, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryResponse> getInventory(@PathVariable UUID productId) {
        InventoryResponse response = inventoryService.getInventoryByProductId(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<InventoryResponse> getInventoryById(@PathVariable UUID inventoryId) {
        InventoryResponse response = inventoryService.getInventoryById(inventoryId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<InventoryResponse> updateStock(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID productId,
            @Valid @RequestBody UpdateStockRequest request) {

        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        String merchantName = jwt.getClaimAsString("name");
        InventoryResponse response = inventoryService.updateStock(clientId, productId, merchantName, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<DeleteInventoryResponse> deleteInventory(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID productId) {

        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        DeleteInventoryResponse response = inventoryService.deleteInventory(clientId, productId);
        return ResponseEntity.ok(response);
    }
}
