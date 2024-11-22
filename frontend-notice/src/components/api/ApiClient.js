import axios from "axios";

/**
 * 
 * Beschreibung:
 * Verbindet die Anwendung mit dem Backend-Server über eine HTTP-Anfragebibliothek.
 **/

// Erzeugt eine Instanz von 'axios' mit vordefinierten Konfigurationen
export const apiClient = axios.create({
    
    // Basis-URL für alle Anfragen, die mit dieser Instanz gesendet werden
    //baseURL: 'http://192.168.2.57:8080' // IP-Adresse des Netzwerks um die Anwendung auf dem Handy zu testen.
   baseURL: 'http://localhost:8080' //Basis-URL für die lokale Entwicklung
    
});

// Interceptor für Anfragen hinzufügen. globales Authentifizierungssystem
apiClient.interceptors.request.use(
    (config) => {
      // Token aus localStorage holen
      const token = localStorage.getItem("authToken");
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;  // Hier fehlt der Backtick (`) für das Template Literal
      }
      return config; // Konfigurierte Anfrage zurückgeben
    },
    (error) => Promise.reject(error) // Fehler abfangen
  );