FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} ChompApp.jar
ENTRYPOINT ["java", "-jar", "ChompApp.jar"]