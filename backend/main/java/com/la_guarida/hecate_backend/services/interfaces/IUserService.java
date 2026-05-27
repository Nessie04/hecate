package com.la_guarida.hecate_backend.services.interfaces;

import com.la_guarida.hecate_backend.dtos.UserRequest;
import com.la_guarida.hecate_backend.dtos.UserResponse;

import java.util.List;

public interface IUserService {
    UserResponse registerUser(UserRequest request);
    UserResponse getUserById(Long userId);
    UserResponse updateUser(Long userId, UserRequest request);
    void deleteUser(Long userId);
    List<UserResponse> filterUserByName(String name);
    void assignAdminRole(Long userId);
    List<UserResponse> getAllUsers();
}
