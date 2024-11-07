import React, { useState } from 'react';
import  './cs_bComponents/Login.css';

function LoginComponent() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  // Formulardaten beim Absenden verarbeiten
  const handleLogin = (e) => {
    e.preventDefault();
    
    // Einfache Validierung
    if (!email || !password) {
      setErrorMessage('Bitte füllen Sie alle Felder aus!');
      return;
    }

    // Hier kannst du die Login-Logik implementieren (z.B. API-Anfrage)
    setErrorMessage('');
    console.log('E-Mail:', email, 'Passwort:', password);
  };

  return (
    <div className="loginComponent">
      <main className="login-main-content">
        <h1>Login</h1>
        <form className="login-form" onSubmit={handleLogin}>
          {errorMessage && <p className="error-message">{errorMessage}</p>}
          
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
      </main>
    </div>
  );
}

export default LoginComponent;
