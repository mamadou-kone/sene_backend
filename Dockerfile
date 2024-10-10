FROM openjdk:22
ADD target/backend-0.0.1-SNAPSHOT.jar backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT["java","-jar","/backend-0.0.1-SNAPSHOT.jar"]

# Créer un volume pour les fichiers temporaires
VOLUME /tmp

# Copier le fichier JAR dans le conteneur
COPY target/*.jar app.jar

# Définir l'entrée du conteneur
ENTRYPOINT ["java", "-jar", "/app.jar"]