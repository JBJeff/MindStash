package com.restApi.RestApi.Basics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;




    public Note createNote(Long userId, Long categoryId, String title, String content) {
        // Lade User und Category basierend auf den IDs
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        // Erstelle eine neue Note und setze die Eigenschaften
        Note note = new Note();
        note.setUser(user);  // Setze das User-Objekt
        note.setCategory(category);  // Setze das Category-Objekt
        note.setTitle(title);
        note.setContent(content);
        
        return noteRepository.save(note);  // Speichern der Notiz
    }







    
    //Später AUSBAUEN
    public Note getNoteByShareableLink(String shareableLink) {
        return noteRepository.findByShareableLink(shareableLink);  // Abrufen der Notiz anhand des Shareable Links
    }
}