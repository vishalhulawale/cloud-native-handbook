# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application, skipping tests to speed up the process
RUN mvn clean package -DskipTests

# Stage 2: run the application
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port (default is usually 8080)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
