package com.ecomerse.merchantmode.service;

import com.ecomerse.merchantmode.dto.stockReservationdto.ReservationResponse;

import java.util.UUID;

public interface StockReservationService {
    ReservationResponse reserve(UUID productId, UUID clientId, Integer quantity);
    void confirm(UUID reservationId, UUID orderId);
    void release(UUID reservationId);
    void expireOldReservations();
}
