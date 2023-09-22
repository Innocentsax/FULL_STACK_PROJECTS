FROM openjdk:11
EXPOSE 8080
#Using a dummy name fintech_app
ADD target/fintech_app.jar fintech_app.jar
ENTRYPOINT ["java","-jar","/fintech_app.jar"]