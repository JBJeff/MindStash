import React, { useState } from 'react';
import './cs_bComponents/Register.css'; // Füge hier deine CSS Datei ein

function RegisterComponent() {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
  });

  const handleChange = (e) => {
    
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Hier kannst du die Logik für die Registrierung einfügen, z. B. API-Request
    console.log('Registriert mit:', formData);
  };

  return (
    <div className="register-container">
      <h2>Registrieren</h2>
      <form onSubmit={handleSubmit} className="register-form">
        <div className="form-group">
          <label htmlFor="name">Name</label>
          <input
            type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            placeholder="Dein Name"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="email">E-Mail</label>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="Deine E-Mail"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="password">Passwort</label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            placeholder="Dein Passwort"
            required
          />
        </div>

        <button type="submit" className="submit-btn">Registrieren</button>
      </form>
    </div>
  );
}

export default RegisterComponent;
