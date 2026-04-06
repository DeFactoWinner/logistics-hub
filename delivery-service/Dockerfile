FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew :delivery-service:clean :delivery-service:bootJar

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/delivery-service/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]