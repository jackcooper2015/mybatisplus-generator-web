FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/code-generator-3.0.1.jar /app/code-generator.jar
ENV GENERATOR_SERVER_ADDRESS=0.0.0.0
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "/app/code-generator.jar"]
