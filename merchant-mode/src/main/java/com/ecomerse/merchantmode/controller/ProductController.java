package com.ecomerse.merchantmode.controller;

import com.ecomerse.merchantmode.dto.productdto.CreateProductRequest;
import com.ecomerse.merchantmode.dto.productdto.DeleteProductResponse;
import com.ecomerse.merchantmode.dto.productdto.ProductResponse;
import com.ecomerse.merchantmode.dto.productdto.UpdateProductRequest;
import com.ecomerse.merchantmode.service.ProductService;
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
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateProductRequest request) {

        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        String merchantName = jwt.getClaimAsString("name");
        ProductResponse response = productService.createProduct(clientId, merchantName, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(@AuthenticationPrincipal Jwt jwt) {
        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        List<ProductResponse> response = productService.getProductsByClientId(clientId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable UUID productId) {
        ProductResponse response = productService.getProductById(productId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID productId,
            @Valid @RequestBody UpdateProductRequest request) {

        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        String merchantName = jwt.getClaimAsString("name");
        ProductResponse response = productService.updateProduct(clientId, productId, merchantName, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<DeleteProductResponse> deleteProduct(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID productId) {

        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        DeleteProductResponse response = productService.deleteProduct(clientId, productId);
        return ResponseEntity.ok(response);
    }
}
