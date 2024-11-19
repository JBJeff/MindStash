import React, { useState } from 'react';
import './dashBoardCSS/CategoryNotes.css';

export default function CategoryNotes() {
    // Zustand für Notizen
    const [notes, setNotes] = useState([
      "Notiz 1: Dies ist eine Beispiel-Notiz zur Kategorie.",
      "Notiz 2: Eine weitere Beispiel-Notiz, die zu dieser Kategorie gehört.",
      "Notiz 3: Hier könnte Ihre Notiz stehen! Dies ist ein Platzhalter.",
      "Notiz 4: Noch eine Notiz, um die Liste etwas zu füllen.",
      "Notiz 5: Es ist wichtig, dass Notizen gut organisiert sind."
    ]);
  
    // Zustand für die neue Notiz, die der Benutzer eingibt
    const [newNote, setNewNote] = useState('');
  
    // Funktion zum Hinzufügen einer Notiz
    const handleAddNote = () => {
      if (newNote.trim()) {
        setNotes([...notes, newNote]);  // Neue Notiz hinzufügen
        setNewNote('');  // Eingabefeld zurücksetzen
      } else {
        alert('Bitte eine Notiz eingeben!');
      }
    };
  
    return (
      <div className="category-notes">
        <h2>Notizen für Kategorie</h2>
  
        {/* Eingabefeld für neue Notiz */}
        <input
          type="text"
          value={newNote}
          onChange={(e) => setNewNote(e.target.value)}
          placeholder="Neue Notiz hinzufügen"
        />
        {/* Button zum Hinzufügen der Notiz */}
        <button className="add-note-button" onClick={handleAddNote}>
          Notiz Hinzufügen
        </button>
  
        <div className="notes-list">
          <ul>
            {notes.length === 0 ? (
              <p>Keine Notizen für diese Kategorie.</p>
            ) : (
              notes.map((note, index) => (
                <li key={index} className="note">
                  {note}
                </li>
              ))
            )}
          </ul>
        </div>
      </div>
    );
  }