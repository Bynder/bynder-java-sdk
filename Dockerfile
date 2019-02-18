FROM java:8

# Install maven
RUN apt-get update
RUN apt-get install -y maven

WORKDIR /app

# Prepare by downloading dependencies
ADD pom.xml /app/pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "install", "-Dgpg.skip", "-Dmaven.javadoc.skip"]

# Adding source, compile and package into a jar
ADD src /app/src
ADD target /app/target