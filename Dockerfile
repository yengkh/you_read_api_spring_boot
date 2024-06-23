# First stage: build the application
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Second stage: create the runtime image
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/target/you_read_api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
