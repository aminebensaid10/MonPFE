# Utiliser l'image OpenJDK 17 comme base
FROM openjdk:17

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le jar Spring Boot construit dans le conteneur
COPY target/pfe-0.0.1-SNAPSHOT.jar /app/app.jar

# Commande de démarrage de l'application Spring Boot
CMD ["java", "-jar", "app.jar"]
