FROM ubuntu:latest

MAINTAINER mehdi "mehdizebhi@gmail.com"

ENV port=8080
ENV jdbcurl=jdbc:mysql://localhost:3307/blog
ENV dbuser=root
ENV dbpass=db.123456

RUN apt-get update && apt-get install -y openjdk-16-jdk

EXPOSE 8080

WORKDIR /usr/local/bin/

ADD target/blog-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "blog-0.0.1-SNAPSHOT.jar"]
