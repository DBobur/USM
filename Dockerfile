
# Asosiy rasm
FROM openjdk:17-jdk-alpine

# Ishchi katalogni o'rnatish
WORKDIR /app

# Loyiha fayllarini konteynerga nusxalash
COPY . .

# JAR faylni qurish
RUN ./gradlew build

# JAR faylni ishga tushirish
ENTRYPOINT ["java", "-jar", "build/libs/USM-0.0.1-SNAPSHOT.jar"]
