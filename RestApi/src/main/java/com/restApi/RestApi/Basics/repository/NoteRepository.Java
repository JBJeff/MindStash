package com.restApi.RestApi.Basics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    
    
    
    





    
    //SPÄTER AUSBAUEN
    Note findByShareableLink(String shareableLink);  // Sucht nach einer Notiz über den shareableLink, 
}