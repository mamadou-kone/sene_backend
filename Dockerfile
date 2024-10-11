FROM openjdk:22
WORKDIR /sene
RUN mvn clean package -DskipTests

# Copier le fichier JAR dans le conteneur
COPY target/backend-0.0.1-SNAPSHOT.jar /sene/backend.jar

# Définir l'entrée du conteneur
ENTRYPOINT ["java", "-jar", "/sene/backend.jar"]