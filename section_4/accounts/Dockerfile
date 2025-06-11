#Start with a base image containing Java runtime
FROM openjdk:21-jdk-slim

#Information around who maintains the image
LABEL "org.opencontainers.image.authors"="ranjitkokare"

# Add the applications's JAR file to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# Execcute the application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]