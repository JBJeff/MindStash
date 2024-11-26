package com.restApi.RestApi.Basics.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.restApi.RestApi.Basics.JWT.JWTUtility;
import com.restApi.RestApi.Basics.dto.UserLoginRequest;
import com.restApi.RestApi.Basics.dto.UserRegistrationRequest;
import com.restApi.RestApi.Basics.dto.UserResponseDTO;
import com.restApi.RestApi.Basics.entity.User;
import com.restApi.RestApi.Basics.repository.UserRepository;
import com.restApi.RestApi.Basics.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    //   @Autowired
    // private JWTUtility jwtUtility;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        // Prüfen, ob die E-Mail bereits registriert ist
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-Mail bereits registriert");
        }

        // Neues Benutzerobjekt erstellen
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword())); // Passwort sicher hashen
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());

        // Benutzer in der Datenbank speichern
        userRepository.save(user);

        return ResponseEntity.ok("Benutzer erfolgreich registriert");
    }


    // // Login-Endpunkt mit JWT-Token
    // @PostMapping("/login")
    // public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request) {
    //     Optional<User> optionalUser = userService.loginUser(request.getEmail(), request.getPassword());

    //     if (optionalUser.isPresent()) {
    //         User user = optionalUser.get();
    //         // JWT-Token generieren
    //         //String token = jwtUtility.generateToken(String.valueOf(user.getId()));
    //         return ResponseEntity.ok(Map.of("token", token));
    //     }

    //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    // }

    // // Login-Endpunkt ohne JWT Token
    // @PostMapping("/login")
    // public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request) {
    //     Optional<User> optionalUser = userService.loginUser(request.getEmail(), request.getPassword());

    //     if (optionalUser.isPresent()) {
    //         // Login erfolgreich, hier könntest du ein Token generieren (für später)
    //         User user = optionalUser.get();
    //         return ResponseEntity.ok(new UserResponseDTO(
    //                 user.getId(),
    //                 user.getEmail(),
    //                 user.getFirstName(),
    //                 user.getLastName(),
    //                 user.getCreatedAt(),
    //                 user.getIsActive()
    //         ));
    //     }

    //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    // }
}
