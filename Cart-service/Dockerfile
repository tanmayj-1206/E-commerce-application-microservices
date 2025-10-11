FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:26-slim-bookworm
WORKDIR /app
COPY --from=build /app/target/cart-service.jar cart-service.jar
ENTRYPOINT ["java", "-jar", "cart-service.jar"]