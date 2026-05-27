package com.la_guarida.hecate_backend.dtos;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String fullName;
    private String email;
    private String password;
    private LocalDate birthDate;
    private String phone;
    private String profilePicture;
}
