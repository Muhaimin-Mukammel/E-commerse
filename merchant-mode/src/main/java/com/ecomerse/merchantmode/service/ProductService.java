package com.ecomerse.merchantmode.service;

import com.ecomerse.merchantmode.dto.productdto.CreateProductRequest;
import com.ecomerse.merchantmode.dto.productdto.DeleteProductResponse;
import com.ecomerse.merchantmode.dto.productdto.ProductResponse;
import com.ecomerse.merchantmode.dto.productdto.UpdateProductRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponse createProduct(UUID clientId, String merchantName, @Valid CreateProductRequest request);
    List<ProductResponse> getProductsByClientId(UUID clientId);
    ProductResponse getProductById(UUID productId);
    ProductResponse updateProduct(UUID clientId, UUID productId, String merchantName, @Valid UpdateProductRequest request);
    DeleteProductResponse deleteProduct(UUID clientId, UUID productId);
}
