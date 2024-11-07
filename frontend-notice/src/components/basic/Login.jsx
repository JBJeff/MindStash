import { Await, BrowserRouter, Route, Routes, useNavigate } from 'react-router-dom'
import React, { useRef, useState } from 'react'


/**
 * Autor: Jeffrey Böttcher
 * Version: 1.0
 * 
 * Beschreibung:
 * Die `LoginComponent`-Komponente stellt ein Anmeldeformular bereit, bei dem Benutzer ihren Benutzernamen und ihr Passwort eingeben können.
 * Bei erfolgreicher Authentifizierung wird der Benutzer auf eine Willkommensseite weitergeleitet. Bei Fehlschlägen wird eine Fehlermeldung angezeigt.
 */
// function WelcomePage() {
//     return (
//       <div className="welcome-page">
//         <header className="header">
//           <h1>Willkommen auf meiner Webseite!</h1>
//           <p>Schön, dass du hier bist. Entdecke unsere Inhalte und genieße deinen Besuch.</p>
//         </header>
//         <main className="main-content">
//           <button className="explore-button">Entdecken</button>
//         </main>
//         <footer className="footer">
//           <p>&copy; 2024 Mein Webprojekt. Alle Rechte vorbehalten.</p>
//         </footer>
//       </div>
//     );
//   }
  
//   export default WelcomePage;