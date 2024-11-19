package com.restApi.RestApi.Basics.service;

import com.restApi.RestApi.Basics.entity.User;
import com.restApi.RestApi.Basics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        // Weitere Benutzerinformationen hinzufügen, falls erforderlich
        return userRepository.save(user);
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
