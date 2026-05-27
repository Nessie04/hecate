package com.la_guarida.hecate_backend.services.interfaces;

import com.la_guarida.hecate_backend.dtos.ReservationRequest;
import com.la_guarida.hecate_backend.dtos.ReservationResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface IReservationService {
    ReservationResponse createReservation(Long userId, ReservationRequest request);
    List<ReservationResponse> getByUserId(Long userId);
    List<ReservationResponse> filterReservation(Long userId, LocalDateTime dateTime, String serviceName, String status);
    void updateReservation(Long reservationId, ReservationRequest request);
    void deleteReservation(Long reservationId);
}
