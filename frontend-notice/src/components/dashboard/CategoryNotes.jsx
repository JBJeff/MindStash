import React, { useState, useEffect } from 'react';
import './dashBoardCSS/CategoryNotes.css';
import { useParams, useNavigate } from 'react-router-dom';
import { createNote } from "../api/NoteApiService";
import { jwtDecode } from "jwt-decode"; 
import { getCategoriesForUser } from '../api/CategoryApiService';  // Ändere den Pfad entsprechend


export default function CategoryNotes({ categories }) {
  const { categoryId } = useParams();  // Die Kategorie-ID aus der URL
  const navigate = useNavigate(); 
  const [category, setCategory] = useState(null);  // Zustand für die aktuelle Kategorie
  const [newNote, setNewNote] = useState('');  // Zustand für die neue Notiz
  const [notes, setNotes] = useState([]);  // Zustand für die Notizen
  const [userId, setUserId] = useState(null);  // Benutzer-ID
  const [loading, setLoading] = useState(true);  // Ladezustand

  // Benutzer-ID aus dem Token abrufen
  const getUserIdFromToken = () => {
    const token = localStorage.getItem("authToken");  // Hole das Token aus localStorage

    if (!token) {
      console.error("Kein Authentifizierungstoken gefunden.");
      return null;
    }
    try {
      const decodedToken = jwtDecode(token);  // Token dekodieren
      console.log("Dekodiertes Token:", decodedToken);  // Ausgabe zur Überprüfung
      return decodedToken.userId;  // Benutzer-ID zurückgeben
    } catch (error) {
      console.error("Fehler beim Dekodieren des Tokens:", error);
      return null;
    }
  };

  // Effekt zum Abrufen der Benutzer-ID und der Kategorie
  useEffect(() => {
    const userIdFromToken = getUserIdFromToken();
    if (!userIdFromToken) {
      console.error("Benutzer-ID nicht verfügbar.");
      //navigate("/login");  // Umleitung zur Login-Seite, wenn kein Token vorhanden ist
      return;
    }
    setUserId(userIdFromToken);  // Benutzer-ID im Zustand setzen
    fetchCategoryAndNotes(userIdFromToken, categoryId);  // Abrufen der Kategorie und Notizen
  }, [categoryId, navigate]);

  // Funktion zum Abrufen der Kategorie und Notizen
  const fetchCategoryAndNotes = async (userId, categoryId) => {
    try {
      const data = await getCategoriesForUser(userId);  // Hole alle Kategorien für den Benutzer
      console.log("Abgerufene Kategorien:", data);  // Ausgabe zur Überprüfung
      const selectedCategory = data.find(cat => cat.id === parseInt(categoryId));
      
      if (selectedCategory) {
        setCategory(selectedCategory);  // Kategorie setzen
        setNotes(selectedCategory.notes || []);  // Notizen setzen (falls vorhanden)
      } else {
        console.error("Kategorie nicht gefunden");
        //navigate("/mainDashBoard");  // Zurück zum Dashboard, wenn die Kategorie nicht gefunden wird
      }
    } catch (error) {
      console.error("Fehler beim Abrufen der Kategorie:", error.message);
      //navigate("/mainDashBoard");  // Im Fehlerfall zur Dashboard-Seite navigieren
    }
    setLoading(false);  // Ladezustand zurücksetzen
  };

  // Funktion zum Hinzufügen einer Notiz
const handleAddNote = async () => {
  if (newNote.trim()) {
    try {
      // Benutzer-ID aus dem Token abrufen
      const userIdFromToken = getUserIdFromToken();  // Holen der Benutzer-ID aus dem Token
      if (!userIdFromToken) {
        alert("Benutzer-ID konnte nicht abgerufen werden.");
        return;
      }

      // Versuche, die Notiz über die API hinzuzufügen
      const newNoteData = await createNote(userIdFromToken, categoryId, 'Neuer Titel', newNote); // Benutzer-ID übergeben

      // Wenn die Notiz erfolgreich erstellt wurde, aktualisiere die Notizenliste
      setNotes([...notes, newNoteData]); // Neue Notiz zur Liste hinzufügen
      setNewNote(''); // Eingabefeld zurücksetzen
    } catch (error) {
      alert('Fehler beim Hinzufügen der Notiz: ' + error.message);
    }
  } else {
    alert('Bitte eine Notiz eingeben!');
  }
};



return (
  <div className="category-notes">
    {loading ? (
      <p>Lade Kategorie und Notizen...</p> // Ladeanzeige
    ) : category ? (
      <>
        <h2>Notizen für {category.name}</h2> {/* Zeige den Namen der Kategorie */}

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
              notes.map((note, index) => ( // liste aller Notizen
                <li key={index} className="note">
                  {note} {/* Zeigt den Notizinhalt */} 
                </li>
              ))
            )}
          </ul>
        </div>
      </>
    ) : (
      <p>Die Kategorie wurde nicht gefunden.</p> // Wenn keine Kategorie gefunden wurde
    )}
  </div>
);


}
