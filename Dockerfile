FROM maven:3.5.2-jdk-8-alpine AS MAVEN
COPY . /tmp/
WORKDIR /tmp/
RUN mvn clean install -Pdocker

FROM openjdk:8-jdk-alpine
COPY --from=MAVEN /tmp/web/target/*.jar app.jar
EXPOSE 8093
ENTRYPOINT ["java", "-jar", "app.jar"]