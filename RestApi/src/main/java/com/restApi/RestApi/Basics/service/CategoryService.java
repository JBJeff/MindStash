package com.restApi.RestApi.Basics.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restApi.RestApi.Basics.entity.Category;
import com.restApi.RestApi.Basics.entity.User;
import com.restApi.RestApi.Basics.repository.CategoryRepository;
import com.restApi.RestApi.Basics.repository.UserRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // Methode, um eine neue Kategorie zu erstellen
    public Category createCategory(Long userId, String categoryName) {
        // Benutzer aus der Datenbank laden
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Neue Kategorie erstellen und Benutzer zuweisen
        Category category = new Category();
        category.setName(categoryName);
        category.setUser(user);  // Kategorie dem Benutzer zuweisen
        category.setCreatedAt(LocalDateTime.now());  // Erstellungsdatum setzen

        // Kategorie speichern
        return categoryRepository.save(category);
    }

    // Methode, um alle Kategorien eines Nutzers zu finden
    public List<Category> getCategoriesForUser(Long userId) {
        return categoryRepository.findByUserId(userId);
    }
}