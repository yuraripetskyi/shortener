FROM maven:3.6.3-jdk-11 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:13-jdk-alpine
COPY --from=build /usr/src/app/target/shortener-*.jar /app/application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/application.jar"]