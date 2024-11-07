function WelcomeComponent() {
    return (
      <div className="welcomeComponent">
        <header className="welcomeHeader">
          <h1>Willkommen auf meiner Webseite!</h1>
          <p>Schön, dass du hier bist. Entdecke unsere Inhalte und genieße deinen Besuch.</p>
        </header>
        <main className="main-content">
          <button className="explore-button">Entdecken</button>
        </main>
        <footer className="footer">
          <p>&copy; 2024 Mein Webprojekt. Alle Rechte vorbehalten.</p>
        </footer>
      </div>
    );
  }
  
  export default WelcomeComponent;