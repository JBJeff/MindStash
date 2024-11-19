package com.restApi.RestApi.Basics.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restApi.RestApi.Basics.entity.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {
    // Optional: Du kannst benutzerdefinierte Abfragen hinzufügen, z.B. nach noteId filtern
    List<Media> findByNoteId(Integer noteId);
}

