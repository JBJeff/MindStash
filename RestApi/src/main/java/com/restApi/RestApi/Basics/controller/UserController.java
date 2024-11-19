package com.restApi.RestApi.Basics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restApi.RestApi.Basics.entity.User;
import com.restApi.RestApi.Basics.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationRequest request) {
        User newUser = userService.createUser(
                request.getEmail(),
                request.getPasswordHash(),
                request.getFirstName(),
                request.getLastName()
        );
        return ResponseEntity.ok(newUser);
    }
}

// Hilfsklasse für die Registrierung
class UserRegistrationRequest {
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;

    // Getter und Setter
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}