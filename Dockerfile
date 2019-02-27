FROM maven:3.6.0-jdk-11-slim
WORKDIR /billing
VOLUME /tmp
ENV JAVA_OPTS=""

ADD pom.xml /billing/pom.xml
ADD src /billing/src
RUN ["mvn", "clean"]
RUN ["mvn", "package"]

RUN cp /billing/target/billing.jar ./app.jar

EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTS \
 -Djava.security.egd=file:/dev/./urandom \
 -Dspring.profiles.active=prod \
 -jar app.jar