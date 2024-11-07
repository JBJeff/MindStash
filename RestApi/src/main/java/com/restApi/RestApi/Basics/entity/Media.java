package com.restApi.RestApi.Basics.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "note_id", nullable = false)
    private Note note; // Referenz zur Notiz

    @Column(name = "url", nullable = false)
    private String url; // URL für das Bild oder Link

    @Column(name = "type", nullable = false)
    private String type; // Typ des Mediums (Bild, Link, etc.)

    // Getter und Setter
}
