# Asosiy rasm
FROM openjdk:17-jdk-alpine

# Ishchi katalogni yaratish
WORKDIR /app

# JAR faylini konteynerga nusxalash
COPY build/libs/USM-0.0.1-SNAPSHOT.jar app.jar

# JAR faylini ishga tushirish
ENTRYPOINT ["java", "-jar", "app.jar"]
