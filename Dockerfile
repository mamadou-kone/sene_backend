FROM eclipse-temurin:22-jdk-alpine

# Créer un volume pour les fichiers temporaires
VOLUME /tmp

# Copier le fichier JAR dans le conteneur
COPY target/*.jar app.jar

# Définir l'entrée du conteneur
ENTRYPOINT ["java", "-jar", "/app.jar"]