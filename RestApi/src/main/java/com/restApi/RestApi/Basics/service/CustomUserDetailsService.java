package com.restApi.RestApi.Basics.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.restApi.RestApi.Basics.entity.User;
import com.restApi.RestApi.Basics.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // @Override
    // public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    //     // Benutzer anhand der E-Mail suchen
    //     var optionalUser = userService.getUserByEmail(email);
        
    //     if (optionalUser.isEmpty()) {
    //         throw new UsernameNotFoundException("Benutzer mit E-Mail " + email + " wurde nicht gefunden.");
    //     }

    //     User user = optionalUser.get();

    //     // Benutzer in ein UserDetails-Objekt umwandeln
    //     return new org.springframework.security.core.userdetails.User(
    //         user.getEmail(),
    //         user.getPasswordHash(),
    //         user.getIsActive(), // Benutzer aktiviert
    //         true,               // Konto nicht abgelaufen
    //         true,               // Anmeldeinformationen nicht abgelaufen
    //         true,               // Konto nicht gesperrt
    //         new ArrayList<>()   // Rollen/Berechtigungen
    //     );
    // }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Benutzer nicht gefunden: " + email));
        
        // Konvertiere User in ein UserDetails-Objekt
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                user.getIsActive(),
                true,  // accountNonExpired
                true,  // credentialsNonExpired
                true,  // accountNonLocked
                List.of() // Rollen (z. B. "ROLE_USER")
        );
    }
}
