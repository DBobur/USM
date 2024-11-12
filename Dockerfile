# Asosiy rasm
FROM openjdk:17-jdk-alpine

# Ishchi katalogni yaratish va belgilash
WORKDIR /app

# Barcha loyiha fayllarini konteyner ichiga nusxalash
COPY . .

# gradlew faylni bajariladigan qilish
RUN chmod +x gradlew

# JAR faylni qurish
RUN ./gradlew build

# JAR faylni ishga tushirish
ENTRYPOINT ["java", "-jar", "build/libs/USM-0.0.1-SNAPSHOT.jar"]
