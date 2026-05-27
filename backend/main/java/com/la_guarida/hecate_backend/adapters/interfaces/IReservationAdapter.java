package com.la_guarida.hecate_backend.adapters.interfaces;

import com.la_guarida.hecate_backend.dtos.ReservationRequest;
import com.la_guarida.hecate_backend.models.ReservationModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IReservationAdapter {
    List<ReservationModel> findAllByUserId(Long userId);
    List<ReservationModel> findByFilters(Long userId, LocalDateTime date, String serviceName, String status);
    Optional<ReservationModel> findById(Long reservationId);
    ReservationModel createReservation(ReservationModel reservation);
    void updateReservation(Long reservationId, ReservationRequest request);
    void deleteReservation(Long reservationId);
    boolean existsByUserIdAndDateTime(Long userId, LocalDateTime dateTime);
}
