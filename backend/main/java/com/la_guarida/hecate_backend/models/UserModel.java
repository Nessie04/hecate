package com.la_guarida.hecate_backend.models;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.la_guarida.hecate_backend.dtos.ReservationResponse;
import com.la_guarida.hecate_backend.dtos.UserResponse;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String fullName;

    @Column (unique = true, nullable = false)
    private String email;

    @Column (nullable = false)
    private String password;

    @Column
    private LocalDate birthDate;

    @Column
    private String profilePicture;

    @Column (unique = true)
    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ReservationModel> reservations = new ArrayList<>();

    public enum Role {
        USER, ADMIN
    }
    @Column
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;


    public UserResponse mapToResponse() {
        UserResponse response = new UserResponse();

        response.setUserId(this.getUserId());
        response.setFullName(this.getFullName());
        response.setEmail(this.getEmail());
        response.setBirthDate(this.getBirthDate());
        response.setPhone(this.getPhone());
        response.setProfilePicture(this.getProfilePicture());

        if (this.reservations != null) {
            List<ReservationResponse> dtos = this.reservations.stream()
                    .map(res -> new ReservationResponse(
                            res.getReservationId(),
                            this.getUserId(),
                            res.getDateTime(),
                            res.getServiceName(),
                            res.getStatus()
                    )).toList();
            response.setReservations(dtos);
        }

        return response;
    }
}
