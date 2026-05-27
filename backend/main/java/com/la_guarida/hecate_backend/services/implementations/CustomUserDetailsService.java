package com.la_guarida.hecate_backend.services.implementations;

import com.la_guarida.hecate_backend.adapters.interfaces.IUserAdapter;
import com.la_guarida.hecate_backend.models.UserModel;
import com.la_guarida.hecate_backend.services.interfaces.UserDetailService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailService {
    private final IUserAdapter adapter;

    public CustomUserDetailsService(IUserAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = adapter.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

}
