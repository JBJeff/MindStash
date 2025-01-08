package com.restApi.RestApi.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.restApi.RestApi.Basics.entity.User;
import com.restApi.RestApi.Basics.repository.UserRepository;
import com.restApi.RestApi.Basics.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test Benutzer erstellen - Erfolg
    @Test
    void testCreateUser_Success() {
        String email = "test@t-online.de";
        String rawPassword = "password123";
        String hashedPassword = "hashedPassword";
        String firstName = "Jeff";
        String lastName = "Boe";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(rawPassword)).thenReturn(hashedPassword);

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPasswordHash(hashedPassword);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setIsActive(true);

        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User result = userService.createUser(email, rawPassword, firstName, lastName);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(hashedPassword, result.getPasswordHash());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertTrue(result.getIsActive());
        verify(userRepository, times(1)).save(any(User.class));
    }

    // Test Benutzer erstellen - Fehler bei existierender E-Mail
    @Test
    void testCreateUser_EmailAlreadyExists() {
        String email = "test@t-online.de";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(email, "password123", "Jeff", "Boe");
        });

        assertEquals("Ein Benutzer mit dieser E-Mail existiert bereits!", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    // Test Benutzer-Login - Erfolg
    @Test
    void testLoginUser_Success() {
        String email = "test@t-online.de";
        String rawPassword = "password123";
        String hashedPassword = "hashedPassword";

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(hashedPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, hashedPassword)).thenReturn(true);

        Optional<User> result = userService.loginUser(email, rawPassword);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
    }

    // Test Benutzer-Login - Fehler bei ungültigem Passwort
    @Test
    void testLoginUser_InvalidPassword() {
        String email = "test@t-online.de";
        String rawPassword = "password123";
        String hashedPassword = "hashedPassword";

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(hashedPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, hashedPassword)).thenReturn(false);

        Optional<User> result = userService.loginUser(email, rawPassword);

        assertTrue(result.isEmpty());
    }

    // Test Benutzer-Login - Fehler bei ungültiger E-Mail
    @Test
    void testLoginUser_InvalidEmail() {
        String email = "test@t-online.de";
        String rawPassword = "password123";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = userService.loginUser(email, rawPassword);

        assertTrue(result.isEmpty());
    }

    // Test Benutzer aktualisieren - Erfolg
    @Test
    void testUpdateUser_Success() {
        Long userId = 1L;
        String firstName = "Jeff";
        String lastName = "Boe";
        Boolean isActive = true;

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFirstName("OldName");
        existingUser.setLastName("OldLastName");
        existingUser.setIsActive(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        Optional<User> result = userService.updateUser(userId, firstName, lastName, isActive);

        assertTrue(result.isPresent());
        assertEquals(firstName, result.get().getFirstName());
        assertEquals(lastName, result.get().getLastName());
        assertEquals(isActive, result.get().getIsActive());
    }

    // Test Benutzer löschen - Erfolg
    @Test
    void testDeleteUser_Success() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);

        boolean result = userService.deleteUser(userId);

        assertTrue(result);
        verify(userRepository, times(1)).deleteById(userId);
    }

    // Test Benutzer löschen - Benutzer nicht gefunden
    @Test
    void testDeleteUser_NotFound() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(false);

        boolean result = userService.deleteUser(userId);

        assertFalse(result);
        verify(userRepository, never()).deleteById(userId);
    }
}
