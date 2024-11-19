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




    // Methode zum Erstellen einer neuen Notiz innerhalb einer Kategorie
    public Note createNote(Long userId, Long categoryId, String title, String content) {
        // Benutzer aus der Datenbank laden
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Kategorie aus der Datenbank laden
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        // Neue Notiz erstellen und setzen
        Note note = new Note();
        note.setUser(user);  // Setze den Benutzer
        note.setCategory(category);  // Setze die Kategorie der Notiz
        note.setTitle(title);
        note.setContent(content);
        
        return noteRepository.save(note);  // Speichern der Notiz
    }

    //Später AUSBAUEN
    public Note getNoteByShareableLink(String shareableLink) {
        return noteRepository.findByShareableLink(shareableLink);  // Abrufen der Notiz anhand des Shareable Links
    }
}