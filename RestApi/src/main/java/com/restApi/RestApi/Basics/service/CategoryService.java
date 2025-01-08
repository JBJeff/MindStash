package com.restApi.RestApi.Basics.service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restApi.RestApi.Basics.entity.Category;
import com.restApi.RestApi.Basics.entity.Note;
import com.restApi.RestApi.Basics.entity.User;
import com.restApi.RestApi.Basics.exception.ResourceNotFoundException;
import com.restApi.RestApi.Basics.repository.CategoryRepository;
import com.restApi.RestApi.Basics.repository.UserRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;


    //Kategorie erstellen
    public Category createCategory(Long userId, String categoryName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Category category = new Category();
        category.setName(categoryName);
        category.setUser(user);
        category.setCreatedAt(LocalDateTime.now());

        return categoryRepository.save(category);
    }

    public List<Category> getCategoriesForUser(Long userId) {
        return categoryRepository.findByUserId(userId);
    }

    // Notiz zu einer Kategorie hinzufügen
    public Category addNoteToCategory(Long categoryId, String noteContent) {
    Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new IllegalArgumentException("Category not found"));

    
    if (category.getNotes() == null) {
        category.setNotes(new ArrayList<>());
    }

    
    Note newNote = new Note();
    newNote.setContent(noteContent); 
    newNote.setCategory(category);  

    category.getNotes().add(newNote);

    
    return categoryRepository.save(category);
}





    // Methode zum Löschen einer Kategorie
    public void deleteCategory(Long categoryId) {
        // Überprüfen, ob die Kategorie existiert
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Kategorie nicht gefunden"));

        // Löschen der Kategorie
        categoryRepository.delete(category);
    }

}
