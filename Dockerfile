FROM openjdk:8-jdk-slim
COPY --chown=coopeuch:coopeuch build/libs/*.jar app.jar
EXPOSE 8030
ENTRYPOINT ["java","-jar","app.jar"]