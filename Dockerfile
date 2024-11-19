FROM gradle:7.5-jdk17-alpine AS builder
WORKDIR /app
COPY . .
RUN gradle build

FROM openjdk:17-jdk-alpine
COPY --from=builder /app/build/libs/USM-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
