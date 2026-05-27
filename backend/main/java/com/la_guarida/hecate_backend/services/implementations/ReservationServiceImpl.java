package com.la_guarida.hecate_backend.services.implementations;

import com.la_guarida.hecate_backend.adapters.interfaces.IReservationAdapter;
import com.la_guarida.hecate_backend.adapters.interfaces.IUserAdapter;
import com.la_guarida.hecate_backend.dtos.ReservationRequest;
import com.la_guarida.hecate_backend.dtos.ReservationResponse;
import com.la_guarida.hecate_backend.models.ReservationModel;
import com.la_guarida.hecate_backend.models.UserModel;
import com.la_guarida.hecate_backend.services.interfaces.IReservationService;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements IReservationService {
    private final IReservationAdapter reservationAdapter;
    private final IUserAdapter userAdapter;

    public ReservationServiceImpl(IReservationAdapter adapter, IUserAdapter userAdapter) {
        this.reservationAdapter = adapter;
        this.userAdapter = userAdapter;
    }

    @Override
    public ReservationResponse createReservation(Long userId, ReservationRequest request) {
        if (reservationAdapter.existsByUserIdAndDateTime(userId, request.getDateTime())) {
            throw new RuntimeException("Ya tienes otra reserva programada para la misma fecha y hora");
        }
        UserModel user = userAdapter.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ReservationModel newReservation = new ReservationModel();

        newReservation.setUserId(userId);
        newReservation.setDateTime(request.getDateTime());
        newReservation.setServiceName(request.getServiceName());
        newReservation.setStatus("CONFIRMADA");

        ReservationModel reservationSaved = reservationAdapter.createReservation(newReservation);

        String fileName = "resguardo_reserva_" + reservationSaved.getReservationId() + ".txt";

        try (FileWriter fw = new FileWriter(fileName);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println("====================================================");
            pw.println("        ✨ LA GUARIDA DE HÉCATE - RESGUARDO ✨        ");
            pw.println("====================================================");
            pw.println(" Reserva : " + reservationSaved.getReservationId());
            pw.println(" Usuario   : " + user.getFullName());
            pw.println(" Email : " + user.getEmail());
            pw.println("----------------------------------------------------");
            pw.println(" Servicio   : " + reservationSaved.getServiceName().toUpperCase());
            pw.println(" Fecha y Hora  : " + reservationSaved.getDateTime());
            pw.println(" Estado   : " + reservationSaved.getStatus());
            pw.println("----------------------------------------------------");
            pw.println("   Guarda este documento. Preséntalo al llegar a    ");
            pw.println("                  nuestro refugio.                    ");
            pw.println("====================================================");
            pw.println(" Generado el: " + java.time.LocalDateTime.now());
        } catch (IOException e) {
            System.err.println("Error al generar el archivo de resguardo " + e.getMessage());
        }
        return reservationSaved.mapToResponse();
    }

    @Override
    public List<ReservationResponse> getByUserId(Long userId) {
        List<ReservationModel> reservationModels = reservationAdapter.findAllByUserId(userId);
        List<ReservationResponse> reservationResponses = new ArrayList<>();

        for (ReservationModel model : reservationModels) {
            ReservationResponse dto = model.mapToResponse();
            reservationResponses.add(dto);
        }
        return reservationResponses;
    }

    @Override
    public List<ReservationResponse> filterReservation(Long userId, LocalDateTime dateTime, String serviceName, String status) {
        return reservationAdapter.findByFilters(userId, dateTime, serviceName, status)
                .stream()
                .map(ReservationModel::mapToResponse)
                .toList();
    }

    @Override
    public void updateReservation(Long reservationId, ReservationRequest request) {
        reservationAdapter.updateReservation(reservationId, request);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        reservationAdapter.findById(reservationId).ifPresent(existingReservation -> reservationAdapter.deleteReservation(reservationId));

    }
}
