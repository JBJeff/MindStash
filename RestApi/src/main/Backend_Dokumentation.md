
# Projekt-Dokumentation

## Projektvision
Die Anwendung ist ein REST-basiertes Backend, das die Verwaltung von Benutzern, Kategorien, Notizen und Medien ermöglicht. Hauptanwendungsfälle:
1. Registrierung und Authentifizierung von Benutzern.
2. Erstellung, Verwaltung und Abruf von Kategorien und Notizen.
3. Hinzufügen und Abrufen von Medien zu Notizen.

## Plattform
Die Anwendung basiert auf:
- **Programmiersprache**: Java 17
- **Framework**: Spring Boot 3.3.4
- **Build-Tool**: Maven
- **Datenbank**: H2 (in-memory)
- **JWT**: JSON Web Tokens zur Authentifizierung
- **Libraries**:
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-security`
  - `io.jsonwebtoken` (Version 0.11.5 für JWT-Handling)
  - `spring-boot-starter-validation`

## Architektur
- **Architekturtyp**: Monolithisch
- **Schichtenmodell**: 3-Schicht-Architektur
  1. **Controller-Schicht**: API-Endpunkte (z.B. `UserController`, `NoteController`)
  2. **Service-Schicht**: Geschäftsdaten-Logik
  3. **Datenzugriffsschicht**: Zugriff auf die H2-Datenbank mit JPA
- **Sicherheitskonzept**:
  - JWT für Authentifizierung und Autorisierung
  - CORS-Konfiguration erlaubt spezifische Frontend-Zugriffe

## Kurzanleitung
### Anwendung starten
1. Stellen Sie sicher, dass Java 17 und Maven installiert sind.
2. Klone das Repository und navigiere zum Projektordner.
3. Führe den folgenden Befehl aus, um die Anwendung zu starten:
   ```bash
   mvn spring-boot:run
   ```

### Verfügbare Endpunkte
- **Benutzer:**
  - Registrierung: `POST /api/users/register`
    ```json
    {
      "email": "hannes@example.com",
      "password": "pass1234",
      "firstName": "hannes",
      "lastName": "Schlüter"
    }
    ```
  - Login: `POST /api/users/login`
    ```json
    {
      "email": "hannes@example.com",
      "password": "Jeffrey1234"
    }
    ```

- **Kategorien:**
  - Kategorie erstellen: `POST /api/categories/{userId}`
    ```json
    {
      "name": "Studium"
    }
    ```
  - Kategorien abrufen: `GET /api/categories/{userId}`

- **Notizen:**
  - Notiz hinzufügen: `POST /api/notes/addNote`
    ```json
    {
      "userId": 1,
      "categoryId": 2,
      "title": "Programmieren 3",
      "content": "Java Spring Boot(restApi) und React(Frontend)"
    }
    ```

- **Medien:**
  - Datei hochladen: `POST /api/media/upload/{noteId}` (Multipart-Request)

### Testdaten
Verwende folgende Dummy-Daten, um die Anwendung zu testen:
- **Benutzer:**
  - Email: `user@example.com`
  - Passwort: `password123`

### Datenbank
Die Anwendung verwendet eine H2-In-Memory-Datenbank. Der Datenbank-Console kann unter `http://localhost:8080/h2-console` erreicht werden.
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Benutzername**: `sa`
- **Passwort**: (kein Passwort erforderlich)


