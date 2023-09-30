FROM openjdk:17 as base
WORKDIR /app
LABEL MAINTAINER ="sulaiman olayinka huzain "holayinka@rocketmail.com""
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src
COPY ./target/pay_buddy-0.0.1-SNAPSHOT.jar  /opt/pay_buddy.jar
COPY . /opt/
ENTRYPOINT ["java", "-jar", "/opt/pay_buddy.jar", "--server.port=8080", "--spring.config.location=file:/opt/src/main/resources/"]

FROM base as development
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000'"]

FROM base as build
RUN ./mvnw package

FROM openjdk:17 as production
EXPOSE 8080
COPY --from=build /app/target/pay-*.jar /pay-buddy.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/pay-buddy.jar"]
FROM openjdk:17


#FROM openjdk:17
#EXPOSE 8080
#LABEL MAINTAINER ="sulaiman olayinka huzain "holayinka@rocketmail.com""
#COPY ./target/blog-app-0.0.1-SNAPSHOT.jar  /opt/blogme.jar
#COPY . /opt/
#ENTRYPOINT ["java", "-jar", "/opt/blogme.jar", "--server.port=8080", "--spring.config.location=file:/opt/src/main/resources/"]
#

