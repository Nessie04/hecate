package com.la_guarida.hecate_backend.controllers;


import com.la_guarida.hecate_backend.adapters.interfaces.IUserAdapter;
import com.la_guarida.hecate_backend.dtos.AuthResponse;
import com.la_guarida.hecate_backend.dtos.LoginRequest;
import com.la_guarida.hecate_backend.dtos.UserRequest;
import com.la_guarida.hecate_backend.dtos.UserResponse;
import com.la_guarida.hecate_backend.models.UserModel;
import com.la_guarida.hecate_backend.security.JwtUtils;
import com.la_guarida.hecate_backend.services.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final IUserAdapter userAdapter;

    public UserController(IUserService userService,
                          AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils,
                          IUserAdapter userAdapter) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userAdapter = userAdapter;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserRequest request) {
        try {
        UserResponse response = userService.registerUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId) {
        try {
        UserResponse response =  userService.getUserById(userId);
        return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long userId, @RequestBody UserRequest request) {
        try {
            userService.updateUser(userId, request);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        try {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<?> assignAdminRole(@PathVariable("userId") Long userId) {
        try {
            userService.assignAdminRole(userId);
            return ResponseEntity.ok("Rol de administrador asignado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserResponse> response =  userService.getAllUsers();
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> filterUserByName(@RequestParam("name") String name) {
        try {
            List<UserResponse> response = userService.filterUserByName(name);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword());
            authenticationManager.authenticate(token);
            UserModel user = userAdapter.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            String jwt = jwtUtils.generateToken(user.getEmail(), user.getUserId());
            AuthResponse authResponse = new AuthResponse(
                    jwt,
                    user.getUserId(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getRole()
            );
            return ResponseEntity.ok(authResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
