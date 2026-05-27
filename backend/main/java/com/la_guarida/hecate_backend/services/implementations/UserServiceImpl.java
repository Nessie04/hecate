package com.la_guarida.hecate_backend.services.implementations;

import com.la_guarida.hecate_backend.adapters.interfaces.IUserAdapter;
import com.la_guarida.hecate_backend.dtos.UserRequest;
import com.la_guarida.hecate_backend.dtos.UserResponse;
import com.la_guarida.hecate_backend.models.UserModel;
import com.la_guarida.hecate_backend.services.interfaces.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    private final IUserAdapter adapter;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(IUserAdapter adapter, PasswordEncoder passwordEncoder) {
        this.adapter = adapter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse registerUser(UserRequest request) {
        UserModel newUser = new UserModel();
        if (adapter.findIfExistsByEmail(request.getEmail())) {
            throw new RuntimeException("El usuario ya existe.");
        }
        newUser.setFullName(request.getFullName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setBirthDate(request.getBirthDate());
        newUser.setPhone(request.getPhone());
        newUser.setProfilePicture(request.getProfilePicture());

        UserModel userCreated = adapter.createUser(newUser);
        return userCreated.mapToResponse();
    }

    @Override
    public UserResponse getUserById(Long userId) {
        UserModel userModel = adapter.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return userModel.mapToResponse();
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest request) {
        UserModel existingUser = adapter.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            existingUser.setEmail(request.getEmail());
            existingUser.setPassword(request.getPassword());
            existingUser.setPhone(request.getPhone());
            existingUser.setProfilePicture(request.getProfilePicture());

            UserModel userUpdated = adapter.updateUser(existingUser);

            return userUpdated.mapToResponse();
    }

    @Override
    public void deleteUser(Long userId) {
        adapter.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            adapter.deleteUser(userId);

    }

    @Override
    public List<UserResponse> filterUserByName(String name) {
        return adapter.filterByName(name)
                .stream()
                .map(UserModel::mapToResponse)
                .toList();
    }

    @Override
    public void assignAdminRole(Long userId) {
        UserModel user = adapter.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setRole(UserModel.Role.ADMIN);
        adapter.updateUser(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return adapter.getAllUsers()
                .stream()
                .map(UserModel::mapToResponse).
                toList();
    }


}
