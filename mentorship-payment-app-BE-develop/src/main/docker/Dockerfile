FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} FintechApp.jar
ENTRYPOINT ["java", "-jar", "FintechApp.jar"]