@echo off

REM Starte das Backend
cd /d D:\Study_Projects\ProgrammierenIII\RestApi
start mvn spring-boot:run

REM Starte das Frontend
cd /d D:\Study_Projects\ProgrammierenIII\frontend-notice
start npm run dev

echo Backend und Frontend wurden gestartet!
