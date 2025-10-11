FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:26-slim-bookworm
WORKDIR /app
COPY --from=build /app/target/api-gateway.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]