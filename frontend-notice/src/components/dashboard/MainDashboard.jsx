import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getCategoriesForUser, createCategory, deleteCategory } from '../api/CategoryApiService';


 // Funktion zum Abrufen der Kategorien eines Benutzers
 const getUserIdFromToken = () => {
  const token = localStorage.getItem("authToken"); // Token abrufen
  if (!token) {
    console.error("Kein Authentifizierungstoken gefunden.");
    return null;
  }

  try {
    const decodedToken = jwt_decode(token); // Token dekodieren
    if (!decodedToken.userId) {
      console.error("Benutzer-ID ist im Token nicht vorhanden.");
      return null;
    }
    return decodedToken.userId;
  } catch (error) {
    console.error("Fehler beim Dekodieren des Tokens:", error);
    return null;
  }
};


export default function MainDashboard() {
  const [categories, setCategories] = useState([]);  // Zustand für die Liste der erzeugten Kategorien
  const [newCategoryName, setNewCategoryName] = useState('');  // Zustand für den neuen Kategoriennamen
  const [userId, setUserId] = useState(null);  // Zustand für die Benutzer-ID

  useEffect(() => {
    const id = getUserIdFromToken();
    if (!id) {
      console.error("Benutzer-ID nicht verfügbar. Umleitung zur Login-Seite.");
      
      //window.location.href = "/login";
    } else {
      setUserId(id);
    }
  }, []);

  // Funktion zum Abrufen der Kategorien von der API
  const fetchCategories = async () => {
    if (!userId) {
      console.error("Benutzer-ID nicht gefunden");
      return;
    }

    try {
      const data = await getCategoriesForUser(userId);

      if (Array.isArray(data)) {
        setCategories(data); // Kategorien in den Zustand speichern
      } else {
        console.error("Die Antwort von der API ist kein Array");
        setCategories([]); // Leere Liste setzen, falls keine Array-Antwort kommt
      }
    } catch (error) {
      console.error("Fehler beim Abrufen der Kategorien:", error.message);
      setCategories([]); // Leere Liste setzen, wenn ein Fehler auftritt
    }
  };

  // Funktion zum Erstellen einer neuen Kategorie
  const handleCreateCategory = async () => {
    if (!userId) {
      console.error("Benutzer-ID fehlt. Bitte melden Sie sich erneut an.");
      return;
    }

    if (newCategoryName.trim()) {
      const newCategory = {
        name: newCategoryName,
      };

      try {
        await createCategory(userId, newCategory);
        fetchCategories(); // Lade die Kategorien neu
        setNewCategoryName(""); // Setzt das Eingabefeld zurück
      } catch (error) {
        console.error("Fehler beim Erstellen der Kategorie:", error.message);
      }
    } else {
      alert("Bitte einen Namen für die Kategorie eingeben!");
    }
  };

  // Funktion zum Löschen einer Kategorie
  const handleDeleteCategory = async (categoryId) => {
    try {
      await deleteCategory(categoryId);
      fetchCategories(); // Lade die Kategorien neu
    } catch (error) {
      console.error("Fehler beim Löschen der Kategorie:", error.message);
    }
  };

  // Lade die Kategorien beim ersten Rendern
  useEffect(() => {
    if (userId) {
      fetchCategories();
    } else {
      console.error("Benutzer-ID ist nicht verfügbar, Benutzer ist nicht authentifiziert.");
    }
  }, [userId]); // Es wird erneut aufgerufen, wenn sich die Benutzer-ID ändert

  return (
    <div className="main-dashboard">
      <h1>Main Dashboard</h1>

      <div className="create-category-section">
        <input
          type="text"
          value={newCategoryName}
          onChange={(e) => setNewCategoryName(e.target.value)}
          placeholder="Gib den Namen der neuen Kategorie ein"
        />
        <button onClick={handleCreateCategory}>Erstelle Kategorie</button>
      </div>

      <div className="categories-list">
        <h2>Erstellte Kategorien</h2>
        {categories.length === 0 ? (
          <p>Keine Kategorien erstellt. Klicke auf "Erstelle Kategorie", um zu starten.</p>
        ) : (
          <ul>
            {categories.map(category => (
              <li key={category.id} className="category">
                <Link to={`/categoryNotes/${category.id}`}>{category.name}</Link>
                <button onClick={() => handleDeleteCategory(category.id)} className="delete-category-button">
                  Löschen
                </button>
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
}
