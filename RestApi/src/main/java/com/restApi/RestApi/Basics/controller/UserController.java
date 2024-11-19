package com.restApi.RestApi.Basics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restApi.RestApi.Basics.dto.UserRegistrationRequest;
import com.restApi.RestApi.Basics.dto.UserResponseDTO;
import com.restApi.RestApi.Basics.entity.User;
import com.restApi.RestApi.Basics.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        User newUser = userService.createUser(
                request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName()
        );

        // Konvertierung zur DTO für die Antwort
        UserResponseDTO response = new UserResponseDTO(
                newUser.getId(),
                newUser.getEmail(),
                newUser.getFirstName(),
                newUser.getLastName(),
                newUser.getCreatedAt(),
                newUser.getIsActive()
        );

        return ResponseEntity.ok(response);
    }
}
