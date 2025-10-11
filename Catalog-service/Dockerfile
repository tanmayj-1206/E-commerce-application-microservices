FROM maven:3.9.6-eclipse-temurin-24 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:26-slim-bookworm
WORKDIR /app
COPY --from=build /app/target/catalog-service.jar catalog-service.jar
ENTRYPOINT ["java", "-jar", "catalog-service.jar"]