package com.restApi.RestApi.Basics.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restApi.RestApi.Basics.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Findet einen Benutzer anhand seiner E-Mail-Adresse (wir gehen davon aus, dass die E-Mail eindeutig ist)
    Optional<User> findByEmail(String email);

    // Optional: Findet einen Benutzer anhand der ID (obwohl JpaRepository diese Methode bereits bietet)
    Optional<User> findById(Long id);

    // Optional: Findet einen Benutzer, der aktiv ist
    List<User> findByIsActive(Boolean isActive);
}
