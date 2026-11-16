package com.ecomerse.merchantmode.repository;

import com.ecomerse.merchantmode.entity.StockReservation;
import com.ecomerse.merchantmode.status.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockReservaionRepository extends JpaRepository<StockReservation, UUID> {
    List<StockReservation> findByReservationStatusAndExpiredAtBefore(ReservationStatus reservationStatus, Instant expiredAt);
    Optional<StockReservation> findByReservationIdAndReservationStatus(UUID reservationId, ReservationStatus reservationStatus);
}
