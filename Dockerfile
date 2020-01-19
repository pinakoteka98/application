FROM ubuntu:18.04

RUN apt update
RUN apt -y install openjdk-8-jdk

ADD target/todolist-0.0.1-SNAPSHOT.jar /opt/todolist.jar

EXPOSE 8080

ENTRYPOINT ["/usr/bin/java", "-jar", "/opt/todolist.jar"]