FROM openjdk:22
WORKDIR /sene


# Copier le fichier JAR dans le conteneur
COPY backend/target/backend-0.0.1-SNAPSHOT.jar /sene/backend.jar

# Définir l'entrée du conteneur
ENTRYPOINT ["java", "-jar", "/sene/backend.jar"]