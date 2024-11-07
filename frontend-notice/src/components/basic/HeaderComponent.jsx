import {  useState } from "react"
import { useNavigate } from "react-router-dom"
import { Link } from 'react-router-dom'; 
import  './cs_bComponents/Header.css';

function HeaderComponent() {
  // Zustand für den Login-Status
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const navigate = useNavigate();

  // Funktion für das Einloggen
  const handleLogin = () => {
    navigate('/login');
  };

  // Funktion für das Ausloggen
  const handleLogout = () => {
    setIsLoggedIn(false);
  };

  return (
    <header className="header">
      <div className="logo">
        {/* Hier das Logo in einen Link einbetten, der zur Startseite führt */}
        <Link to="/" >
          MindStash
        </Link>
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