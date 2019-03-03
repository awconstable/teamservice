FROM openjdk:11.0.2-jdk-slim

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/teamservice/teamservice.jar"]

# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/teamservice/teamservice.jar
