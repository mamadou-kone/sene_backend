# Utilise une image de base contenant OpenJDK 17 et Maven
FROM maven:3.8.6-openjdk-17-slim AS build

# Définit le répertoire de travail
WORKDIR /app

# Copie tous les fichiers du projet dans le conteneur
COPY . .

# Compile votre application pour générer le fichier JAR
RUN mvn clean package -DskipTests

# Utilise l'image de base OpenJDK 17 pour l'exécution
FROM openjdk:17-jdk-slim

# Expose le port 8080
EXPOSE 8080

# Copie le fichier JAR généré dans le conteneur d'exécution
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Définit la commande pour exécuter l'application
CMD ["java", "-jar", "app.jar"]
