import React, { useState } from 'react';
import  './cs_bComponents/Login.css';
import { useNavigate } from "react-router-dom"
const jwt_decode = (await import("jwt-decode")).default;


import { loginUser } from '../api/UserApiService';

function LoginComponent() {
  const [errorMessage, setErrorMessage] = useState('');
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null);

  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault(); // Verhindert das Standard-Verhalten (Seiten-Neuladen)
    setErrorMessage(null);  // Fehler zurücksetzen
    setSuccessMessage(null);  // Erfolg zurücksetzen

    const userData = { email, password };

    try {
      // API-Aufruf zum Login
      const response = await loginUser(userData);

      if (response.token) {
        // Token im localStorage speichern
        localStorage.setItem("authToken", response.data.token);

        //  Benutzer-ID aus dem Token dekodieren und speichern
        const decodedToken = jwt_decode(response.token);
        localStorage.setItem("userId", decodedToken.userId);

        setSuccessMessage(`Login erfolgreich! Willkommen, ${decodedToken.firstName || 'Benutzer'}.`);
        navigate('/mainDashBoard'); // Navigiere zur Hauptseite
      } else {
        throw new Error("Kein Token erhalten. Bitte überprüfe die Login-Daten.");
      }
    } catch (error) {
      // Fehlerbehandlung
      setErrorMessage(error.message || "Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
    }
  };

  const handleRegister = () => {
    navigate('/register');
  };

  return (
    <div className="loginComponent">
      <main className="login-main-content">
        <h1>Login</h1>
        
        <form className="login-form" onSubmit={handleLogin}>
          {errorMessage && <p className="error-message">{errorMessage}</p>}
          {successMessage && <p className="success-message">{successMessage}</p>}

          <div className="input-group">
            <label htmlFor="email">E-Mail:</label>
            <input
              type="email"
              id="email"
              name="email"
              placeholder="Deine E-Mail-Adresse"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <div className="input-group">
            <label htmlFor="password">Passwort:</label>
            <input
              type="password"
              id="password"
              name="password"
              placeholder="Dein Passwort"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          <button type="submit" className="login-button">Login</button>
        </form>

        <button className="register-button" onClick={handleRegister}>
          Registrieren
        </button>
      </main>
    </div>
  );
}

export default LoginComponent;
