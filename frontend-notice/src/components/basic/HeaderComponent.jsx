import React, { useState } from 'react';
import  './cs_bComponents/Header.css';

function HeaderComponent() {
  // Zustand für den Login-Status
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // Funktion für das Einloggen
  const handleLogin = () => {
    setIsLoggedIn(true);
  };

  // Funktion für das Ausloggen
  const handleLogout = () => {
    setIsLoggedIn(false);
  };

  return (
    <header className="header">
      <div className="logo">
        <h1>MindStash</h1>
      </div>

      <nav className="nav">
        {/* Menü für Notizen-Dashboard */}
        <a href="/notices" className="link">
          Notizen
        </a>

        {/* Einloggen und Ausloggen Optionen */}
        {isLoggedIn ? (
          <button onClick={handleLogout} className="button">
            Ausloggen
          </button>
        ) : (
          <button onClick={handleLogin} className="button">
            Einloggen
          </button>
        )}
      </nav>
    </header>
  );
}

export default HeaderComponent;