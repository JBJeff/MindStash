import React, { useState } from 'react';
import  './cs_bComponents/Login.css';
import { useNavigate } from "react-router-dom"
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
      // Versuche den Login-API-Aufruf
      const response = await loginUser(userData);
      
      // Erfolgreiche Anmeldung
      setSuccessMessage(`Login successful: ${response.firstName} ${response.lastName}`);
      
      // Hier kannst du z.B. den Benutzer in den Status setzen, ein Token speichern etc.
      // localStorage.setItem("authToken", response.token);  // Falls du Token speicherst
    } catch (error) {
      // Fehlerbehandlung
      setErrorMessage(error.message || "An error occurred. Please try again later.");
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
