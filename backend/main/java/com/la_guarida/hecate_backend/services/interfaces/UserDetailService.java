package com.la_guarida.hecate_backend.services.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailService extends org.springframework.security.core.userdetails.UserDetailsService {
    UserDetails loadUserByUsername(String email);
}
