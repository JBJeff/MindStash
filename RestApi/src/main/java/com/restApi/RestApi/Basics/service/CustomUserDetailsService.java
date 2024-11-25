package com.restApi.RestApi.Basics.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.restApi.RestApi.Basics.entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Benutzer anhand der E-Mail suchen
        var optionalUser = userService.getUserByEmail(email);
        
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Benutzer mit E-Mail " + email + " wurde nicht gefunden.");
        }

        User user = optionalUser.get();

        // Benutzer in ein UserDetails-Objekt umwandeln
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPasswordHash(),
            user.getIsActive(), // Benutzer aktiviert
            true,               // Konto nicht abgelaufen
            true,               // Anmeldeinformationen nicht abgelaufen
            true,               // Konto nicht gesperrt
            new ArrayList<>()   // Rollen/Berechtigungen
        );
    }
}
