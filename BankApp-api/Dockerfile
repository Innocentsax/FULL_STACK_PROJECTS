FROM openjdk:17-oracle
LABEL authors="decagon"
COPY target/goCash-0.0.1-SNAPSHOT.jar goCash-image.jar
EXPOSE 8088
EXPOSE 5432
ENTRYPOINT ["java", "-jar", "goCash-image.jar"]