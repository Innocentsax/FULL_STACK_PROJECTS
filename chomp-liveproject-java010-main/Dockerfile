FROM openjdk:13-jdk-alpine3.10
RUN apk update && apk upgrade && apk add netcat-openbsd && apk add bind-tools
RUN mkdir -p /usr/local/chompapp
ADD target/chompapp-0.0.1-SNAPSHOT.jar /usr/local/chompapp/
CMD java  -Dspring.profiles.active=$PROFILE -jar /usr/local/chompapp/chompapp-0.0.1-SNAPSHOT.jar