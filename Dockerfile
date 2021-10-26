#install OS
#FROM centos
FROM openjdk:8
#install java
#RUN yum install -y java


#put jar into container
#ADD target/AdWordsProducer-1.0-SNAPSHOT-shaded.jar adwordsproducer.jar
ADD target/*-jar-with-dependencies.jar target.jar
#run jar

ENTRYPOINT ["java", "-jar", "/target.jar"]