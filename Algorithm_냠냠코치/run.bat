@echo off
setlocal
if not exist target\yumyum-coach.jar call mvn clean test package
if errorlevel 1 exit /b 1
java -jar target\yumyum-coach.jar
