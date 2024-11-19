package com.restApi.RestApi.Basics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.restApi.RestApi.Basics.entity.Media;
import com.restApi.RestApi.Basics.entity.Note;
import com.restApi.RestApi.Basics.repository.MediaRepository;
import com.restApi.RestApi.Basics.repository.NoteRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    
    @Autowired
    private NoteRepository noteRepository;

    //Bild speichern
    public Media saveMedia(MultipartFile file, Long noteId) throws IOException {
        //Stelle sicher, dass die Note existiert
        
        Optional<Note> noteOptional = noteRepository.findById(noteId);
        if (!noteOptional.isPresent()) {
            throw new RuntimeException("Note with ID " + noteId + " not found");
        }
        Note note = noteOptional.get();

        //Media-Objekt erstellen und speichern
        Media media = new Media();
        media.setNote(note);
        media.setType(file.getContentType());
        media.setData(file.getBytes());  // Speichern der Bilddaten als byte[]

        return mediaRepository.save(media); // Speichern in der Datenbank
    }

    // Alle Medien für eine bestimmte Notiz abrufen
    public List<Media> getMediaByNoteId(Integer noteId) {
        return mediaRepository.findByNoteId(noteId);  // Holt alle Medien zu einer Note
    }

    // Einzelnes Bild (Media) anhand der ID abrufen
    public Media getMediaById(Integer id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found with ID: " + id));
    }

    // Löschen eines Mediums
    public void deleteMedia(Integer id) {
        mediaRepository.deleteById(id);
    }
}
