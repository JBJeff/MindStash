#!/bin/bash


#starte das gesamte Project, führe in der Konsole das aus: "bash start_projects.sh"
# Starte das Backend
echo "Starte das Backend..."
cd /path/to/RestApi || { echo "Backend-Verzeichnis nicht gefunden!"; exit 1; }
gnome-terminal -- bash -c "mvn spring-boot:run; exec bash"

# Starte das Frontend
echo "Starte das Frontend..."
cd /path/to/frontend-notice || { echo "Frontend-Verzeichnis nicht gefunden!"; exit 1; }
gnome-terminal -- bash -c "npm run dev; exec bash"

echo "Backend und Frontend wurden gestartet!"
