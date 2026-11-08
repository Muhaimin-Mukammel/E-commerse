package com.ecomerse.merchantmode.service;

import com.ecomerse.merchantmode.dto.salesdto.CreateSalesRequest;
import com.ecomerse.merchantmode.dto.salesdto.DeleteSalesResponse;
import com.ecomerse.merchantmode.dto.salesdto.SalesResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface SalesService {
    SalesResponse createSales(UUID clientId, @Valid CreateSalesRequest request);
    List<SalesResponse> getSalesByClientId(UUID clientId);
    SalesResponse getSalesById(Long salesId);
    SalesResponse updateSales(UUID clientId, Long salesId, @Valid CreateSalesRequest request);
    DeleteSalesResponse deleteSales(UUID clientId, Long salesId);
}
