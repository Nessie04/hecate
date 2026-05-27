package com.la_guarida.hecate_backend.dtos;

import com.la_guarida.hecate_backend.models.UserModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Long userId;
    private String fullName;
    private String email;
    private UserModel.Role role;
}
