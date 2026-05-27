package com.la_guarida.hecate_backend.adapters.interfaces;

import com.la_guarida.hecate_backend.models.UserModel;

import java.util.List;
import java.util.Optional;

public interface IUserAdapter {
    Optional<UserModel> findById(Long id);
    boolean findIfExistsByEmail(String email);
    Optional<UserModel> findByEmail(String email);
    UserModel createUser(UserModel userModel);
    UserModel updateUser(UserModel userModel);
    void deleteUser(Long userId);
    List<UserModel> getAllUsers();
    List<UserModel> filterByName(String name);
}
