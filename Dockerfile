# Base image
FROM openjdk:17-jdk-alpine

# Application jar file location in the build/libs folder
COPY build/libs/USM-0.0.1-SNAPSHOT.jar /app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
