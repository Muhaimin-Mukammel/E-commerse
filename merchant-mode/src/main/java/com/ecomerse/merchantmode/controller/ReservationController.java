package com.ecomerse.merchantmode.controller;

import com.ecomerse.merchantmode.dto.stockReservationdto.ConfirmReservationRequest;
import com.ecomerse.merchantmode.dto.stockReservationdto.ReleaseReservationRequest;
import com.ecomerse.merchantmode.dto.stockReservationdto.ReservationResponse;
import com.ecomerse.merchantmode.dto.stockReservationdto.ReserveRequest;
import com.ecomerse.merchantmode.service.StockReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final StockReservationService reservationService;

    public ReservationController(StockReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reserve")
    public ResponseEntity<ReservationResponse> reserve(
            @Valid @RequestBody ReserveRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        UUID clientId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
        ReservationResponse response = reservationService.reserve(
                request.productId(),
                clientId,
                request.quantity()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirm(@Valid @RequestBody ConfirmReservationRequest request) {
        reservationService.confirm(request.reservationId(), request.orderId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/release")
    public ResponseEntity<Void> release(@Valid @RequestBody ReleaseReservationRequest request) {
        reservationService.release(request.reservationId());
        return ResponseEntity.ok().build();
    }
}
