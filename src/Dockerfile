# Utilise l'image de base pour Java
        FROM openjdk:17-jdk-slim

        # Définit le répertoire de travail
        WORKDIR /app

        # Copie le fichier JAR de l'application dans l'image
        COPY target/sene_backend-0.0.1-SNAPSHOT.jar app.jar

        # Expose le port 8080
        EXPOSE 8080

        # Commande pour exécuter l'application
        CMD ["java", "-jar", "app.jar"]
