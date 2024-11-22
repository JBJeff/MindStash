import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { getCategoriesForUser, createCategory, deleteCategory } from '../api/CategoryApiService';

// export default function MainDashboard() {
//   const [categories, setCategories] = useState([]);  // Zustand für die Liste der erzeugten Kategorien
//   const [newCategoryName, setNewCategoryName] = useState('');  // Zustand für den neuen Kategoriennamen

//   // Funktion, um eine neue Kategorie zu erstellen
//   const handleCreateCategory = () => {
//     if (newCategoryName.trim()) {
//       const newCategory = {
//         id: Date.now(),  // Einzigartige ID basierend auf der Zeit
//         name: newCategoryName,
//         notes: [],  // Zu jeder Kategorie gehören Notizen
//       };
//       setCategories([...categories, newCategory]);
//       setNewCategoryName('');  // Setzt das Eingabefeld zurück
//     } else {
//       alert('Bitte einen Namen für die Kategorie eingeben!');
//     }
//   };

//   // Funktion, um eine Kategorie zu löschen
//   const handleDeleteCategory = (categoryId) => {
//     const updatedCategories = categories.filter(category => category.id !== categoryId);
//     setCategories(updatedCategories);
//   };

//   return (
//     <div className="main-dashboard">
//       <h1>Main Dashboard</h1>

//       <div className="create-category-section">
//         <input
//           type="text"
//           value={newCategoryName}
//           onChange={(e) => setNewCategoryName(e.target.value)}
//           placeholder="Gib den Namen der neuen Kategorie ein"
//         />
//         <button onClick={handleCreateCategory}>Erstelle Kategorie</button>
//       </div>

//       <div className="categories-list">
//         <h2>Erstellte Kategorien</h2>
//         {categories.length === 0 ? (
//           <p>Keine Kategorien erstellt. Klicke auf "Erstelle Kategorie", um zu starten.</p>
//         ) : (
//           <ul>
//             {categories.map(category => (
//               <li key={category.id} className="category">
//                 {/* Weiterleitung zur CategoryNotes-Seite mit der entsprechenden Kategorie-ID */}
//                 <Link to={`/categoryNotes/${category.id}`}>{category.name}</Link>
//                 <button onClick={() => handleDeleteCategory(category.id)} className="delete-category-button">
//                   Löschen
//                 </button>
//               </li>
//             ))}
//           </ul>
//         )}
//       </div>
//     </div>
//   );
// }

export default function MainDashboard() {
    const [categories, setCategories] = useState([]);  // Zustand für die Liste der erzeugten Kategorien
    const [newCategoryName, setNewCategoryName] = useState('');  // Zustand für den neuen Kategoriennamen
    const userId = 1; // Beispiel für eine Benutzer-ID, normalerweise wird diese dynamisch geladen
  
    // Funktion zum Abrufen der Kategorien von der API
  const fetchCategories = async () => {
    try {
      const data = await getCategoriesForUser(userId);
      
      // Überprüfe, ob die Antwort ein Array ist
      if (Array.isArray(data)) {
        setCategories(data);  // Kategorien in den Zustand speichern
      } else {
        console.error('Die Antwort von der API ist kein Array');
        setCategories([]);  // Leere Liste setzen, falls keine Array-Antwort kommt
      }
    } catch (error) {
      console.error('Fehler beim Abrufen der Kategorien:', error.message);
      setCategories([]);  // Leere Liste setzen, wenn ein Fehler auftritt
    }
  };

  // Funktion zum Erstellen einer neuen Kategorie
  const handleCreateCategory = async () => {
    if (newCategoryName.trim()) {
      const newCategory = {
        name: newCategoryName,
      };

      try {
        await createCategory(userId, newCategory);
        fetchCategories(); // Lade die Kategorien neu
        setNewCategoryName('');  // Setzt das Eingabefeld zurück
      } catch (error) {
        console.error('Fehler beim Erstellen der Kategorie:', error.message);
      }
    } else {
      alert('Bitte einen Namen für die Kategorie eingeben!');
    }
  };

  // Funktion zum Löschen einer Kategorie
  const handleDeleteCategory = async (categoryId) => {
    try {
      await deleteCategory(categoryId);
      fetchCategories(); // Lade die Kategorien neu
    } catch (error) {
      console.error('Fehler beim Löschen der Kategorie:', error.message);
    }
  };

  // Lade die Kategorien beim ersten Rendern
  useEffect(() => {
    fetchCategories();
  }, []);  // Leeres Array bedeutet, dass es nur einmal beim ersten Rendern aufgerufen wird.
  
  
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
