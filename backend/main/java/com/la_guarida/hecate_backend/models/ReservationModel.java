package com.la_guarida.hecate_backend.models;

import com.la_guarida.hecate_backend.dtos.ReservationResponse;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class ReservationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;
    private LocalDateTime dateTime;
    private String serviceName;
    private String status;

    public ReservationModel() {

    }

    public ReservationModel(Long reservationId, UserModel user, LocalDateTime dateTime, String serviceName, String status) {
        this.reservationId = reservationId;
        this.user = user;
        this.dateTime = dateTime;
        this.serviceName = serviceName;
        this.status = status;
    }

    public ReservationResponse mapToResponse() {
        return new ReservationResponse(
                this.reservationId,
                this.user != null ? this.user.getUserId() : null,
                this.dateTime,
                this.serviceName,
                this.status
        );
    }

    public Long getReservationId(){return reservationId;}
    public void setReservationId(Long reservationId) {this.reservationId = reservationId;}

    public UserModel getUser(){return user;}
    public void setUser(UserModel user){this.user = user;}

    public Long getUserId() {
        return this.user != null ? this.user.getUserId() : null;
    }
    public void setUserId(Long userId) {
        if (userId == null) {
            this.user = null;
        } else {
            if (this.user == null) {
                this.user = new UserModel();
            }
            this.user.setUserId(userId);
        }
    }

    public LocalDateTime getDateTime(){return dateTime;}
    public void setDateTime(LocalDateTime dateTime){this.dateTime = dateTime;}

    public String getServiceName(){return serviceName;}
    public void setServiceName(String serviceName) {this.serviceName = serviceName;}

    public String getStatus(){return status;}
    public void setStatus(String status) {this.status = status;}

}
