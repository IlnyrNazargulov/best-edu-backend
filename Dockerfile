FROM openjdk:8-jdk-alpine
EXPOSE 8093
ARG JAR_FILE=web/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]