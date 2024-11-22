package com.restApi.RestApi.Basics.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restApi.RestApi.Basics.entity.Category;
import com.restApi.RestApi.Basics.entity.Note;
import com.restApi.RestApi.Basics.entity.User;
import com.restApi.RestApi.Basics.repository.CategoryRepository;
import com.restApi.RestApi.Basics.repository.UserRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

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

    public Category addNoteToCategory(Long categoryId, String noteContent) {
    Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new IllegalArgumentException("Category not found"));

    // Initialize the notes list if null
    if (category.getNotes() == null) {
        category.setNotes(new ArrayList<>());
    }

    // Create a new Note and associate it with the category
    Note newNote = new Note();
    newNote.setContent(noteContent); // Assuming "content" is the field for note text
    newNote.setCategory(category);  // Set the relationship

    // Add the Note object to the list
    category.getNotes().add(newNote);

    // Save the category (cascades can ensure the note is saved if set up correctly)
    return categoryRepository.save(category);
}
}
