FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine-slim

ADD target/kindredhair-api-grp-1-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]