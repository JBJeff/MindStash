import jwt_decode from 'jwt-decode';


// API-Client für die Authentifizierung
export const fetchUserData = async () => {
    try {
      const response = await apiClient.get("/api/protected/user-data");
      return response.data; // Erfolgreiche Antwort zurückgeben
    } catch (error) {
      // Fehlerbehandlung
      if (error.response) {
        // Server antwortet mit einem Fehlercode
        throw new Error(error.response.data.message || "Unknown error");
      } else if (error.request) {
        // Keine Antwort vom Server
        throw new Error("No response from server");
      } else {
        // Fehler bei der Anfrage
        throw new Error("An error occurred. Please try again later.");
      }
    }
  };

    // Funktion zum Abrufen der Kategorien eines Benutzers
  const getUserIdFromToken = () => {
    const token = localStorage.getItem("authToken");
    if (token) {
      try {
        const decodedToken = jwt_decode(token);
        return decodedToken.userId; // userId aus dem Token extrahieren
      } catch (error) {
        console.error("Fehler beim Dekodieren des Tokens:", error);
        return null; // Falls Token ungültig oder dekodieren fehlschlägt
      }
    }
    return null; // Falls kein Token vorhanden
  };
