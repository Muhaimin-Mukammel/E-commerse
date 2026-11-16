package com.ecomerse.merchantmode.service.Impl;

import com.ecomerse.merchantmode.dto.stockReservationdto.ReservationResponse;
import com.ecomerse.merchantmode.entity.Inventory;
import com.ecomerse.merchantmode.entity.Product;
import com.ecomerse.merchantmode.entity.StockReservation;
import com.ecomerse.merchantmode.repository.InventoryRepository;
import com.ecomerse.merchantmode.repository.ProductRepository;
import com.ecomerse.merchantmode.repository.StockReservaionRepository;
import com.ecomerse.merchantmode.service.StockReservationService;
import com.ecomerse.merchantmode.status.ReservationStatus;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class StockReservationServiceImpl implements StockReservationService {

    private static final Duration EXPIRATION_TIME = Duration.ofMinutes(20);
    private static final int MAX_RETRIES = 3;

    private final StockReservaionRepository stockReservationRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public StockReservationServiceImpl(StockReservaionRepository stockReservationRepository, InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.stockReservationRepository = stockReservationRepository;
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }


    @Override
    @Transactional
    public ReservationResponse reserve(UUID productId, UUID clientId, Integer quantity) {
        if(quantity <= 0 || quantity == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity should be greater than 0");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found"));

        int attempt = 0;
        while (true) {
            try{
                Inventory inventory = inventoryRepository.findByProductProductId(productId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Inventory not found"));

                inventory.reserve(quantity);
                inventoryRepository.save(inventory);

                Instant expiredAt = Instant.now().plus(EXPIRATION_TIME);
                StockReservation reservation = new StockReservation(
                        productId,
                        product.getStore().getPublicStoreId(),
                        clientId,
                        quantity,
                        expiredAt
                );

                stockReservationRepository.save(reservation);
                return mapToReservationResponse(reservation);
            } catch (ObjectOptimisticLockingFailureException e){
                attempt++;
                if(attempt >= MAX_RETRIES){
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            "Could not reserve stock due to concurrent updates. Please try again.");
                }
            } catch (IllegalStateException e){
                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
            }
        }
    }

    @Override
    @Transactional
    public void confirm(UUID reservationId, UUID orderId) {
        StockReservation reservation = stockReservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation not found"));

        if(reservation.getReservationStatus() != ReservationStatus.ACTIVE){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation is not active.");
        }

        if(reservation.isExpired()){
            expireSingle(reservation);
            throw new ResponseStatusException(HttpStatus.GONE, "Reservation has expired.");
        }

        Inventory inventory = inventoryRepository.findByProductProductId(reservation.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Inventory not found"));

        inventory.confirmReservation(reservation.getQuantity());
        inventoryRepository.save(inventory);

        reservation.confirm(orderId);
        stockReservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public void release(UUID reservationId) {
        StockReservation reservation = stockReservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation not found"));

        if(reservation.getReservationStatus() != ReservationStatus.ACTIVE){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation is not active.");
        }

        Inventory inventory = inventoryRepository.findByProductProductId(reservation.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Inventory not found"));

        inventory.releaseReservation(reservation.getQuantity());
        inventoryRepository.save(inventory);

        reservation.release();
        stockReservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public void expireOldReservations() {
        List<StockReservation> expiredReservations = stockReservationRepository.findByReservationStatusAndExpiredAtBefore(ReservationStatus.EXPIRED, Instant.now());

        for(StockReservation reservation : expiredReservations){
            expireSingle(reservation);
        }

    }

    private void expireSingle(StockReservation reservation){
        if (reservation.getReservationStatus() != ReservationStatus.ACTIVE) {
            return;
        }
        /*
        .orElse(null) is used because throwing exception here is not Ideal
         */
        Inventory inventory = inventoryRepository.findByProductProductId(reservation.getProductId())
                .orElse(null);

        if (inventory != null) {
            try {
                inventory.releaseReservation(reservation.getQuantity());
                inventoryRepository.save(inventory);
            } catch (IllegalStateException ex) {
                // waiting for logging
            }
        }

        reservation.expire();
        stockReservationRepository.save(reservation);
    }

    private ReservationResponse mapToReservationResponse(StockReservation reservation) {
        return new ReservationResponse(
                reservation.getReservationId(),
                reservation.getProductId(),
                reservation.getQuantity(),
                reservation.getReservationStatus(),
                reservation.getCreatedAt(),
                reservation.getExpiredAt()
        );
    }
}
