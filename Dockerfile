FROM maven:3.8.6-openjdk:22 AS build
COPY . .
WORKDIR /sene
RUN mvn clean package -DskipTests

# Copier le fichier JAR dans le conteneur
COPY --from=build /sene/target/backend-0.0.1-SNAPSHOT.jar /sene/backend.jar

# Définir l'entrée du conteneur
ENTRYPOINT ["java", "-jar", "/sene/backend.jar"]