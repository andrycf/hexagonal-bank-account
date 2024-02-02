FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar
EXPOSE 8081
ENTRYPOINT [ "java","-jar" , "/app.jar" ]