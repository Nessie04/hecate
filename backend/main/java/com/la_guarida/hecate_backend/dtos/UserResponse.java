package com.la_guarida.hecate_backend.dtos;
import java.time.LocalDate;
import java.util.List;

import com.la_guarida.hecate_backend.models.ReservationModel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long userId;
    private String fullName;
    private String email;
    private LocalDate birthDate;
    private String profilePicture;
    private String phone;
    private List<ReservationResponse> reservations;
}


