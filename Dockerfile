# Asosiy rasm
FROM openjdk:17-jdk-alpine

# JAR faylini konteynerga nusxalash
COPY build/libs/USM-0.0.1-SNAPSHOT.jar /app.jar

# JAR faylini ishga tushirish
ENTRYPOINT ["java", "-jar", "/app.jar"]
