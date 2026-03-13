# Étape 1 : compilation du projet avec Maven
FROM maven:3.9-eclipse-temurin-21 AS etape-compilation
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : lancement de l'application
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=etape-compilation /app/target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
