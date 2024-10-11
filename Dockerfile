# Étape 1 : Installer Maven sur OpenJDK 22
FROM openjdk:22-jdk-slim AS build

# Installer Maven manuellement
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /sene

# Copier le code source dans l'image
COPY . .

# Construire l'application avec Maven
RUN mvn clean package -DskipTests

# Étape 2 : Utiliser OpenJDK 22 pour l'exécution
FROM openjdk:22-jdk-slim

# Copier le fichier JAR depuis l'étape de build
COPY --from=build /sene/target/backend-0.0.1-SNAPSHOT.jar /sene/backend.jar

# Définir l'entrée du conteneur
ENTRYPOINT ["java", "-jar", "/sene/backend.jar"]
