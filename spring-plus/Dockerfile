FROM openjdk:17-jdk-slim

COPY build/libs/*.jar application.jar

ENV SPRING_PROFILES_ACTIVE=test

ENTRYPOINT ["java", "-jar", "/application.jar"]
