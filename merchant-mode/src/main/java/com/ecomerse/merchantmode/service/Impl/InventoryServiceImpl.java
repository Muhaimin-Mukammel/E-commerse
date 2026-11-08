package com.ecomerse.merchantmode.service.Impl;

import com.ecomerse.merchantmode.dto.inventorydto.DeleteInventoryResponse;
import com.ecomerse.merchantmode.dto.inventorydto.InventoryResponse;
import com.ecomerse.merchantmode.dto.inventorydto.UpdateStockRequest;
import com.ecomerse.merchantmode.entity.Inventory;
import com.ecomerse.merchantmode.entity.Merchant;
import com.ecomerse.merchantmode.entity.Product;
import com.ecomerse.merchantmode.repository.InventoryRepository;
import com.ecomerse.merchantmode.repository.MerchantRepository;
import com.ecomerse.merchantmode.repository.ProductRepository;
import com.ecomerse.merchantmode.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.UUID;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final MerchantRepository merchantRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository,
                                ProductRepository productRepository,
                                MerchantRepository merchantRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.merchantRepository = merchantRepository;
    }

    @Override
    @Transactional
    public InventoryResponse createInventory(UUID clientId, UUID productId, String merchantName, UpdateStockRequest request) {
        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (!product.getStore().getPublicStoreId().equals(merchant.getStore().getPublicStoreId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not own this product");
        }

        if(inventoryRepository.findByProductId(productId).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Inventory for this product already exists");
        }

        Inventory inventory = new Inventory(product, request.stock());
        inventory.setLastModifiedBy(merchantName);
        inventoryRepository.save(inventory);
        return mapToResponse(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getInventoryByProductId(UUID productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory for this product not found"));
        return mapToResponse(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getInventoryById(UUID inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found"));
        return mapToResponse(inventory);
    }

    @Override
    @Transactional
    public InventoryResponse updateStock(UUID clientId, UUID productId, String merchantName, UpdateStockRequest request) {
        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (!product.getStore().getPublicStoreId().equals(merchant.getStore().getPublicStoreId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not own this product");
        }

        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory for this product not found"));

        inventory.setStock(request.stock());
        inventory.setLastModifiedBy(merchantName);
        inventory.setUpdatedAt(Instant.now());
        inventoryRepository.save(inventory);
        return mapToResponse(inventory);
    }

    @Override
    @Transactional
    public DeleteInventoryResponse deleteInventory(UUID clientId, UUID productId) {
        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if(!product.getStore().getPublicStoreId().equals(merchant.getStore().getPublicStoreId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not own this product");
        }

        inventoryRepository.deleteById(productId);
        return new DeleteInventoryResponse("Successfully deleted inventory");
    }

    private InventoryResponse mapToResponse(Inventory inventory) {
        return new InventoryResponse(
                inventory.getInventoryId(),
                inventory.getProduct().getProdcutId(),
                inventory.getStock(),
                inventory.getCreatedAt(),
                inventory.getUpdatedAt(),
                inventory.getLastModifiedBy()
        );
    }
}
