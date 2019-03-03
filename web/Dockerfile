FROM openjdk:11.0.2-jdk-slim

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/dashboard/dashboard.jar"]

# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/dashboard/dashboard.jar
