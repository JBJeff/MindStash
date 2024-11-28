package com.restApi.RestApi.Basics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restApi.RestApi.Basics.entity.Category;
import com.restApi.RestApi.Basics.entity.Note;
import com.restApi.RestApi.Basics.entity.User;
import com.restApi.RestApi.Basics.repository.CategoryRepository;
import com.restApi.RestApi.Basics.repository.NoteRepository;
import com.restApi.RestApi.Basics.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // Methode zum Hinzufügen einer Notiz zu einer Kategorie
    @Transactional
    public Note addNoteToCategory(Long userId, Long categoryId, String title, String content) {
        // Überprüfen, ob der Benutzer existiert
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Benutzer nicht gefunden"));

        // Überprüfen, ob die Kategorie existiert
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Kategorie nicht gefunden"));

        // Eine neue Notiz erstellen und die notwendigen Felder setzen
        Note note = new Note();
        note.setUser(user);  // Die Notiz wird mit dem Benutzer verknüpft
        note.setCategory(category);  // Die Notiz wird mit der Kategorie verknüpft
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedAt(LocalDateTime.now());
        note.setIsArchived(false);  // Standardwert (optional)

        // Speichern der Notiz in der Datenbank
        return noteRepository.save(note);
    }

    //Später AUSBAUEN
    // public Note getNoteByShareableLink(String shareableLink) {
    //     return noteRepository.findByShareableLink(shareableLink);  // Abrufen der Notiz anhand des Shareable Links
    // }
}