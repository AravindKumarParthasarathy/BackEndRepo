FROM maven:4.0.0-jdk-17 as build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:17-jre-alpine
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/usr/app/app.jar"]
