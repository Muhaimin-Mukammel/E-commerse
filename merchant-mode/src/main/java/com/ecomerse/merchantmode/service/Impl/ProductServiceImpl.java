package com.ecomerse.merchantmode.service.Impl;

import com.ecomerse.merchantmode.dto.productdto.CreateProductRequest;
import com.ecomerse.merchantmode.dto.productdto.DeleteProductResponse;
import com.ecomerse.merchantmode.dto.productdto.ProductResponse;
import com.ecomerse.merchantmode.dto.productdto.UpdateProductRequest;
import com.ecomerse.merchantmode.entity.Inventory;
import com.ecomerse.merchantmode.entity.Merchant;
import com.ecomerse.merchantmode.entity.Product;
import com.ecomerse.merchantmode.entity.Store;
import com.ecomerse.merchantmode.repository.InventoryRepository;
import com.ecomerse.merchantmode.repository.MerchantRepository;
import com.ecomerse.merchantmode.repository.ProductRepository;
import com.ecomerse.merchantmode.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final MerchantRepository merchantRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    public ProductServiceImpl(MerchantRepository merchantRepository,
                              ProductRepository productRepository,
                              InventoryRepository inventoryRepository) {
        this.merchantRepository = merchantRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }
    @Override
    @Transactional
    public ProductResponse createProduct(UUID clientId, String merchantName, CreateProductRequest request) {
        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));

        Store store = merchant.getStore();
        if(store == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant dose not have an active store");
        }

        Product product = new Product(store, request.productName(), request.productPrice(), request.description());
        product.setLastModifiedBy(merchantName);
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        product.setRaw_version(0);
        productRepository.save(product);

        Inventory inventory = new Inventory(product, request.initialStock() != null ? request.initialStock() : 0);
        inventory.setLastModifiedBy(merchantName);
        inventoryRepository.save(inventory);

        return mapToResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByClientId(UUID clientId) {
        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));

        List<Product> products = productRepository.findByPublicStoreId(merchant.getStore().getPublicStoreId());

        return products.stream().map(this::mapToResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        return mapToResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(UUID clientId, UUID productId, String merchantName, UpdateProductRequest request) {
        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if(!product.getStore().getPublicStoreId().equals(merchant.getStore().getPublicStoreId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Merchant dose not own this product");
        }

        if (request.productName() != null) product.setProductName(request.productName());
        if (request.productPrice() != null) product.setProductPrice(request.productPrice());
        if (request.description() != null) product.setDescription(request.description());
        if (request.status() != null) product.setStatus(request.status());

        product.setLastModifiedBy(merchantName);
        product.setUpdatedAt(Instant.now());

        productRepository.save(product);
        return mapToResponse(product);
    }

    @Override
    @Transactional
    public DeleteProductResponse deleteProduct(UUID clientId, UUID productId) {
        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if(!product.getStore().getPublicStoreId().equals(merchant.getStore().getPublicStoreId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Merchant dose not own this product");
        }

        productRepository.delete(product);
        return new DeleteProductResponse("Product has been deleted");
    }

    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getProdcutId(),
                product.getProductName(),
                product.getProductPrice(),
                product.getStatus(),
                product.getDescription(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getLastModifiedBy()
        );
    }
}
