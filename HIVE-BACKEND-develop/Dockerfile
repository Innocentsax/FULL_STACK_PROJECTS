FROM maven:3.9.1-eclipse-temurin-17-alpine AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM eclipse-temurin:19.0.2_7-jdk-alpine
COPY --from=build /home/app/target/hive.jar /usr/local/lib/hive.jar
EXPOSE 5000
#ADD target/hive.jar hive.jar
ENTRYPOINT ["java","-Xmx512m","-jar","/usr/local/lib/hive.jar"]
CMD ["tail", "-f", "/dev/null"]
