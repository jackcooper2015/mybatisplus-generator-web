FROM java:8

RUN mkdir /app
WORKDIR /app
ADD build/libs/code-generator-0.0.1-SNAPSHOT.jar /app
RUN ln -sf code-generator-0.0.1-SNAPSHOT.jar code-generator-latest.jar
ENTRYPOINT ["java","-jar","/app/code-generator-latest.jar"]