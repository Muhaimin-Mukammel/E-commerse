package com.ecomerse.merchantmode.service.Impl;

import com.ecomerse.merchantmode.dto.salesdto.CreateSalesRequest;
import com.ecomerse.merchantmode.dto.salesdto.DeleteSalesResponse;
import com.ecomerse.merchantmode.dto.salesdto.SalesResponse;
import com.ecomerse.merchantmode.entity.Merchant;
import com.ecomerse.merchantmode.entity.Sales;
import com.ecomerse.merchantmode.entity.SalesItem;
import com.ecomerse.merchantmode.entity.Store;
import com.ecomerse.merchantmode.repository.MerchantRepository;
import com.ecomerse.merchantmode.repository.SalesRepository;
import com.ecomerse.merchantmode.service.SalesService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class SalesServiceImpl implements SalesService {

    private final SalesRepository salesRepository;
    private final MerchantRepository merchantRepository;

    public SalesServiceImpl(SalesRepository salesRepository, MerchantRepository merchantRepository) {
        this.salesRepository = salesRepository;
        this.merchantRepository = merchantRepository;
    }

    @Override
    public SalesResponse createSales(UUID clientId, CreateSalesRequest request) {
        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));

        Store store = merchant.getStore();
        if(store == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant dose not have a store");
        }

        Sales sales = new Sales();
        sales.setStore(store);
        setSalesValue(sales, request);

        salesRepository.save(sales);
        return mapToResponse(sales);
    }

    @Override
    public List<SalesResponse> getSalesByClientId(UUID clientId) {
        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));

        Store store = merchant.getStore();
        if(store == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant dose not have a store");
        }
        List<Sales> sales = salesRepository.findByPublicStoreId(store.getPublicStoreId());
        return sales.stream().map(this::mapToResponse).toList();
    }

    @Override
    public SalesResponse getSalesById(Long salesId) {
        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sales not found"));
        return mapToResponse(sales);
    }

    @Override
    public SalesResponse updateSales(UUID clientId, Long salesId, CreateSalesRequest request) {
        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));

        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sales not found"));

        if(!sales.getStore().getPublicStoreId().equals(merchant.getStore().getPublicStoreId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Merchant dose not own this Sale");
        }

        setSalesValue(sales, request);
        salesRepository.save(sales);

        return mapToResponse(sales);
    }

    @Override
    public DeleteSalesResponse deleteSales(UUID clientId, Long salesId) {
        Merchant merchant = merchantRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));

        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sales not found"));

        if(!sales.getStore().getPublicStoreId().equals(merchant.getStore().getPublicStoreId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Merchant dose not own this Sale");
        }

        salesRepository.delete(sales);
        return new DeleteSalesResponse("Sales has been deleted");
    }

    private SalesResponse mapToResponse(Sales sales) {
        List<SalesResponse.SalesItemInfo> items = sales.getSalesItems().stream()
                .map(item -> new SalesResponse.SalesItemInfo(
                        item.getProductId(),
                        item.getProductName(),
                        item.getQuantitySold(),
                        item.getTotalPrice()
                ))
                .toList();

        return new SalesResponse(
                sales.getSalesId(),
                sales.getStore().getPublicStoreId(),
                sales.getTotalSales(),
                sales.getTotalItemSold(),
                sales.getPeriodStart(),
                sales.getPeriodEnd(),
                sales.getCreatedAt(),
                items
        );
    }

    private void setSalesValue(Sales sales, CreateSalesRequest request) {
        if (request.periodStart() != null) {
            sales.setPeriodStart(request.periodStart());
        }
        if (request.periodEnd() != null) {
            sales.setPeriodEnd(request.periodEnd());
        }
        if(request.items() != null) {
            sales.getSalesItems().clear();

        BigDecimal totalSales = BigDecimal.ZERO;
        int totalItemSold = 0;

        if (request.items() != null) {
            for (CreateSalesRequest.SalesItemRequest itemReq : request.items()) {
                SalesItem item = new SalesItem();
                item.setSales(sales);

                if (itemReq.productId() != null) {
                    item.setProductId(itemReq.productId());
                }
                if (itemReq.productName() != null) {
                    item.setProductName(itemReq.productName());
                }
                if (itemReq.quantitySold() != null) {
                    item.setQuantitySold(itemReq.quantitySold());
                    totalItemSold += itemReq.quantitySold();
                }
                if (itemReq.totalPrice() != null) {
                    item.setTotalPrice(itemReq.totalPrice());
                    totalSales = totalSales.add(itemReq.totalPrice());
                }

                sales.addSalesItem(item);
            }
        }

        sales.setTotalSales(totalSales);
        sales.setTotalItemSold(totalItemSold);
        }
    }
}
