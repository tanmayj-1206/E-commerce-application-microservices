FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:24-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/search-service.jar search-service.jar
ENTRYPOINT ["java", "-jar", "search-service.jar"]