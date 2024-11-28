package com.restApi.RestApi.Basics.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.restApi.RestApi.Basics.entity.Media;
import com.restApi.RestApi.Basics.service.MediaService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    // Bild hochladen
    @PostMapping("/upload/{noteId}")
    public ResponseEntity<Media> uploadMedia(
        @PathVariable Long noteId,
        @RequestParam("file") MultipartFile file
    ) {
        try {
            Media media = mediaService.uploadMedia(noteId, file);
            return new ResponseEntity<>(media, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Bilder für eine Notiz abrufen
    @GetMapping("/note/{noteId}")
    public ResponseEntity<List<Media>> getMediaByNoteId(@PathVariable Integer noteId) {
        return ResponseEntity.ok(mediaService.getMediaByNoteId(noteId));
    }

    // Einzelnes Bild abrufen
    @GetMapping("/{mediaId}")
    public ResponseEntity<byte[]> getMediaById(@PathVariable Integer mediaId) {
        Media media = mediaService.getMediaById(mediaId);
        return ResponseEntity.ok()
                .header("Content-Type", media.getType())
                .body(media.getData());
    }
}
