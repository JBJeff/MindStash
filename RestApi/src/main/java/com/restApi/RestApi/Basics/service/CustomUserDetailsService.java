package com.restApi.RestApi.Basics.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.restApi.RestApi.Basics.dto.CustomUserDetails;
import com.restApi.RestApi.Basics.entity.User;
import com.restApi.RestApi.Basics.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Benutzer nicht gefunden: " + email));
        
        // Gib CustomUserDetails zurück, nicht das Standard User-Objekt
        return new CustomUserDetails(
                user.getEmail(),
                user.getPasswordHash(),
                user.getIsActive(),
                user.getId()  // User ID an CustomUserDetails übergeben
        );
    }
}

