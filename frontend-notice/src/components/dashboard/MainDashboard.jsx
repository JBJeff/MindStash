import React, { useState } from 'react';
import { Link } from 'react-router-dom';

export default function MainDashboard() {
  const [categories, setCategories] = useState([]);  // Zustand für die Liste der erzeugten Kategorien
  const [newCategoryName, setNewCategoryName] = useState('');  // Zustand für den neuen Kategoriennamen

  // Funktion, um eine neue Kategorie zu erstellen
  const handleCreateCategory = () => {
    if (newCategoryName.trim()) {
      const newCategory = {
        id: Date.now(),  // Einzigartige ID basierend auf der Zeit
        name: newCategoryName,
        notes: [],  // Zu jeder Kategorie gehören Notizen
      };
      setCategories([...categories, newCategory]);
      setNewCategoryName('');  // Setzt das Eingabefeld zurück
    } else {
      alert('Bitte einen Namen für die Kategorie eingeben!');
    }
  };

  // Funktion, um eine Kategorie zu löschen
  const handleDeleteCategory = (categoryId) => {
    const updatedCategories = categories.filter(category => category.id !== categoryId);
    setCategories(updatedCategories);
  };

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
                {/* Weiterleitung zur CategoryNotes-Seite mit der entsprechenden Kategorie-ID */}
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
