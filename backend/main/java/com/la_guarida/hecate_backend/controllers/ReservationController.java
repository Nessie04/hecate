package com.la_guarida.hecate_backend.controllers;

import com.la_guarida.hecate_backend.dtos.ReservationRequest;
import com.la_guarida.hecate_backend.dtos.ReservationResponse;
import com.la_guarida.hecate_backend.services.interfaces.IReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final IReservationService reservationService;

    public ReservationController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> createReservation(@PathVariable("userId") Long userId, @RequestBody ReservationRequest request) {
        try {
        ReservationResponse responses = reservationService.createReservation(userId, request);
        return ResponseEntity.ok(responses);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable("userId") Long userId) {
        try {
            List<ReservationResponse> responses = reservationService.getByUserId(userId);
            if (responses != null && !responses.isEmpty()) {
                return ResponseEntity.ok(responses);
            }
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<?> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        try {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<?> updateReservation(@PathVariable("reservationId") Long reservationId, @RequestBody ReservationRequest request) {
        try {
            reservationService.updateReservation(reservationId, request);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterReservation(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "dateTime", required = false) LocalDateTime dateTime,
            @RequestParam(value = "serviceName", required = false) String serviceName,
            @RequestParam(value = "status", required = false) String status) {
        try {
            List<ReservationResponse> reservations =
            reservationService.filterReservation(userId, dateTime, serviceName, status);

            if (reservations != null && !reservations.isEmpty()) {
            return ResponseEntity.ok(reservations);
            }
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
