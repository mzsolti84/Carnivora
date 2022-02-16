FROM openjdk:17.0.2-slim

RUN apt-get update && apt-get install curl -y

ADD target/kozos-projekt-0.0.1-SNAPSHOT.jar /usr/src/app.jar
ENV PROFILES=${PROFILES}

EXPOSE 8080:8080
CMD java -Dspring.profiles.active=${PROFILES} -Xmx64m -Xss256k -XX:+UseSerialGC -Djava.security.egd=file:/dev/./urandom -Dspring.output.ansi.enabled=ALWAYS -jar /usr/src/app.jar
