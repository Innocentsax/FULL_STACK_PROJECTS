FROM openjdk:13-jdk-alpine3.10
RUN apk update && apk upgrade && apk add netcat-openbsd && apk add bind-tools
RUN mkdir -p /usr/local/cedarxpress
ADD target/cedarxpress-0.0.1-SNAPSHOT.jar /usr/local/cedarxpress/
CMD java  -Dspring.profiles.active=$PROFILE -jar /usr/local/cedarxpress/cedarxpress-0.0.1-SNAPSHOT.jar