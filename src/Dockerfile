# Utilise l'image de base OpenJDK 22 pour le build
FROM openjdk:22-jdk-slim AS build

# Définit le répertoire de travail
WORKDIR /app

# Copie les fichiers de votre projet dans le conteneur
COPY . .

# Compile votre application pour générer le fichier JAR
RUN mvn clean package -DskipTests

# Utilise l'image de base OpenJDK 22 pour l'exécution
FROM openjdk:22-jdk-slim

# Définit le répertoire de travail pour l'exécution
WORKDIR /app

# Expose le port 8080
EXPOSE 8080

# Copie le fichier JAR généré dans l'image
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Définit la commande pour exécuter l'application
CMD ["java", "-jar", "app.jar"]
