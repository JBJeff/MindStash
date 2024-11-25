package com.restApi.RestApi.Basics.jwtoken;




/**
 * Autor: Jeffrey Böttcher
 * Version: 1.0
 * 
 * Beschreibung:
 * Die Klasse `JwtTokenRequest` repräsentiert die Anfrage für die JWT-Authentifizierung.
 * Sie enthält die notwendigen Anmeldeinformationen eines Benutzers: den Benutzernamen und das Passwort.
 */
public record JwtTokenRequest(String username, String password) {}













