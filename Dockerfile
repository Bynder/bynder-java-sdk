FROM java:8

# Install maven
RUN apt-get update
RUN apt-get install -y maven

RUN mkdir -p /home/app/java-sdk
WORKDIR /home/app/java-sdk

# Prepare by downloading dependencies
ADD pom.xml /home/app/java-sdk/pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "install", "-Dgpg.skip", "-Dmaven.javadoc.skip"]

# Adding source, compile and package into a jar
ADD src /home/app/java-sdk/src
ADD target /home/app/java-sdk/target