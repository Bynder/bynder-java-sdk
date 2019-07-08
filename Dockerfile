FROM maven:3.6.1-jdk-8

USER root
WORKDIR /app

# Prepare by downloading dependencies
COPY . .
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "install", "-Dgpg.skip", "-Dmaven.javadoc.skip"]

# Compile and package into a jar
RUN ["mvn", "package"]
