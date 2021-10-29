FROM java:8

COPY mc-0.0.1-SNAPSHOT.jar /mc-0.0.1-SNAPSHOT.jar
CMD ["--server.port=8081"]
EXPOSE 8081
ENTRYPOINT ["java","-jar","/mc-0.0.1-SNAPSHOT.jar"]