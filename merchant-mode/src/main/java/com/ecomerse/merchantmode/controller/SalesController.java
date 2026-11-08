package com.ecomerse.merchantmode.controller;

import com.ecomerse.merchantmode.dto.salesdto.CreateSalesRequest;
import com.ecomerse.merchantmode.dto.salesdto.DeleteSalesResponse;
import com.ecomerse.merchantmode.dto.salesdto.SalesResponse;
import com.ecomerse.merchantmode.service.SalesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/sales")
public class SalesController {
    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @PostMapping
    public ResponseEntity<SalesResponse> createSales(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateSalesRequest request) {

        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        SalesResponse response = salesService.createSales(clientId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SalesResponse>> getMySales(@AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        List<SalesResponse> response = salesService.getSalesByClientId(clientId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{salesId}")
    public ResponseEntity<SalesResponse> getSalesById(@PathVariable Long salesId) {
        SalesResponse response = salesService.getSalesById(salesId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{salesId}")
    public ResponseEntity<SalesResponse> updateSales(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long salesId,
            @Valid @RequestBody CreateSalesRequest request) {

        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        SalesResponse response = salesService.updateSales(clientId, salesId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{salesId}")
    public ResponseEntity<DeleteSalesResponse> deleteSales(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long salesId) {

        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        DeleteSalesResponse response = salesService.deleteSales(clientId, salesId);
        return ResponseEntity.ok(response);
    }
}
