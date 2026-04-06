FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew :eureka-server:clean :eureka-server:bootJar

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/eureka-server/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]