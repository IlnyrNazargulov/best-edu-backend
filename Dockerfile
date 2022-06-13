FROM maven:3.8.5-openjdk-11 AS maven_build
RUN mvn package
EXPOSE 8093
FROM openjdk
ARG JAR_FILE=web/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]