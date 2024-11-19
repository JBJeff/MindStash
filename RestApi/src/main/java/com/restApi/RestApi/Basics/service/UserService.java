package com.restApi.RestApi.Basics.service;

import com.restApi.RestApi.Basics.entity.User;
import com.restApi.RestApi.Basics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Benutzer erstellen
   public User createUser(String email, String passwordHash, String firstName, String lastName) {
        // Prüft, ob ein Benutzer mit der E-Mail bereits existiert
        userRepository.findByEmail(email).ifPresent(existingUser -> {
            throw new IllegalArgumentException("Ein Benutzer mit dieser E-Mail existiert bereits!");
        });

        // Neuen Benutzer erstellen
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPasswordHash(passwordHash); // Hash wird angenommen (z.B. durch BCryptEncoder)
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setIsActive(true);

        return userRepository.save(newUser);
    }

    // Benutzer nach ID finden
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Benutzer nach E-Mail finden
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Benutzer aktualisieren
    public Optional<User> updateUser(Long id, String firstName, String lastName, Boolean isActive) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setIsActive(isActive);
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

    // Benutzer löschen
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Benutzer aktivieren/deaktivieren
    public Optional<User> toggleUserStatus(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setIsActive(!user.getIsActive());
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }
}
