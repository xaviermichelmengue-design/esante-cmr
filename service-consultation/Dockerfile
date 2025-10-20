FROM openjdk:17-jdk-slim

# Installer curl pour les health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Créer un utilisateur non-root pour la sécurité
RUN groupadd -r spring && useradd -r -g spring spring
USER spring

WORKDIR /app

# Copier le JAR
COPY target/service-consultation-1.0.0.jar app.jar

# Exposition du port
EXPOSE 8082

# Point d'entrée avec les bonnes pratiques JVM pour les conteneurs
ENTRYPOINT ["java", "-jar", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-XX:+UseContainerSupport", \
            "-XX:MaxRAMPercentage=75.0", \
            "/app.jar"]
