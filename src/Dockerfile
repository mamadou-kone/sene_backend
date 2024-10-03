# Utilise l'image de base OpenJDK 22
FROM openjdk:22-jdk-slim

# Définit le répertoire de travail
WORKDIR /app

# Copie le fichier JAR dans l'image à partir du nouveau chemin
COPY src/main/java/com/sene/backend/backend-0.0.1-SNAPSHOT.jar app.jar

# Expose le port 8080
EXPOSE 8080

# Définit la commande pour exécuter l'application
CMD ["java", "-jar", "app.jar"]
