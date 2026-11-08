package com.ecomerse.merchantmode.service;

import com.ecomerse.merchantmode.dto.inventorydto.DeleteInventoryResponse;
import com.ecomerse.merchantmode.dto.inventorydto.InventoryResponse;
import com.ecomerse.merchantmode.dto.inventorydto.UpdateStockRequest;
import jakarta.validation.Valid;

import java.util.UUID;

public interface InventoryService {
    InventoryResponse createInventory(UUID clientId, UUID productId, String merchantName, @Valid UpdateStockRequest request);
    InventoryResponse getInventoryByProductId(UUID productId);
    InventoryResponse getInventoryById(UUID inventoryId);
    InventoryResponse updateStock(UUID clientId, UUID productId, String merchantName, @Valid UpdateStockRequest request);
    DeleteInventoryResponse deleteInventory(UUID clientId, UUID productId);
}
