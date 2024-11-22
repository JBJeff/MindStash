import React, { useState, useEffect } from 'react';
import './dashBoardCSS/CategoryNotes.css';
import { useParams, useNavigate } from 'react-router-dom';

export default function CategoryNotes({ categories }) {
  const { categoryId } = useParams(); // Die Kategorie-ID aus der URL
  const navigate = useNavigate(); 
  const [category, setCategory] = useState(null); // Zustand für die aktuelle Kategorie
  const [newNote, setNewNote] = useState(''); // Zustand für die neue Notiz
  const [notes, setNotes] = useState([]); // Zustand für die Notizen 

  // Findet die Kategorie anhand der Kategorie-ID
  useEffect(() => {
    if (categories) {
      const selectedCategory = categories.find(cat => cat.id === parseInt(categoryId));
      if (selectedCategory) {
        setCategory(selectedCategory);
        setNotes(selectedCategory.notes || []); // Setze Notizen der gefundenen Kategorie (kann null sein)
      } else {
        navigate('/mainDashBoard'); // Wenn keine Kategorie gefunden wird, navigiere zurück zum Dashboard
      }
    }
  }, [categoryId, categories, navigate]);

  // Funktion zum Hinzufügen einer Notiz
  const handleAddNote = () => {
    if (newNote.trim()) {
      const updatedNotes = [...notes, newNote];
      setNotes(updatedNotes);
      setNewNote(''); // Eingabefeld zurücksetzen
    } else {
      alert('Bitte eine Notiz eingeben!');
    }
  };

  return (
    <div className="category-notes">
      {category ? (
        <>
          <h2>Notizen für {category.name}</h2> {/* Zeige den Namen der aktuellen Kategorie an */}
          
          {/* Eingabefeld und Button zum Hinzufügen einer neuen Notiz */}
          <div className="add-note-section">
            <input
              type="text"
              value={newNote}
              onChange={(e) => setNewNote(e.target.value)}
              placeholder="Neue Notiz hinzufügen"
            />
            <button className="add-note-button" onClick={handleAddNote}>
              Notiz Hinzufügen
            </button>
          </div>

          {/* Liste der Notizen */}
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
        </>
      ) : (
        <p>Die Kategorie wurde nicht gefunden.</p> // Wenn keine Kategorie gefunden wird
      )}
    </div>
  );
}
