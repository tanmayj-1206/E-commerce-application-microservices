FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:24-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/inventory-service.jar inventory-service.jar
ENTRYPOINT ["java", "-jar", "inventory-service.jar"]