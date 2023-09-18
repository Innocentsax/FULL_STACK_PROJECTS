# syntax=docker/dockerfile:1

FROM eclipse-temurin:11-jdk-jammy as base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src

FROM base as development
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000'"]

FROM base as build
RUN ./mvnw package

FROM eclipse-temurin:11-jre-jammy as production
EXPOSE 8080
COPY --from=build /app/target/DecaPay-Java012-Backend-*.jar /DecaPay-Java012-Backend.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/DecaPay-Java012-Backend.jar"]