#FROM ubuntu:latest AS build
#RUN apt-get update
#RUN apt-get install openjdk-21-jdk -y
#
## Set the working directory inside the container
#WORKDIR /app
#
## Copy the entire project into the container
#COPY . .
#
## Run the Gradle build
#RUN gradle build --no-daemon
#
## Use a minimal base image with OpenJDK 21 runtime
#FROM openjdk:21-jre-slim
#
## Set the working directory inside the container
#WORKDIR /app
#
## Copy the built jar from the build stage
#COPY --from=build /app/build/libs/*.jar /app/market-data-microservice.jar
#
## Expose the port the application runs on
#EXPOSE 8082
#
## Set the entrypoint command to run the application
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]


# Use the official OpenJDK 21 image as the base image
#FROM openjdk:21-jdk-slim
#
## Set the working directory in the container
#WORKDIR /app
#RUN curl -L https://services.gradle.org/distributions/gradle-6.5.1-bin.zip -o gradle-6.5.1-bin.zip
#RUN unzip gradle-6.5.1-bin.zip
#ENV GRADLE_HOME=/app/gradle-6.5.1
#ENV PATH=$PATH:$GRADLE_HOME/bin
#RUN gradle --version
## Copy the Gradle wrapper and project files
#COPY gradlew ./
#COPY gradle ./gradle
#COPY build.gradle ./
#COPY settings.gradle ./
#
## Copy the application source code
#COPY src ./src
#
## Give execution permissions to the Gradle wrapper
#RUN chmod +x ./gradlew
#
## Build the application
#RUN gradle clean build
#
## Expose the port the application runs on
#EXPOSE 8080
#
## Set the entrypoint for the container to run the application
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]

FROM openjdk:21-jdk
RUN apt-get update && apt-get install -y unzip
WORKDIR /gradle
RUN curl -L https://services.gradle.org/distributions/gradle-6.5.1-bin.zip -o gradle-6.5.1-bin.zip
RUN unzip gradle-6.5.1-bin.zip
ENV GRADLE_HOME=/gradle/gradle-6.5.1
ENV PATH=$PATH:$GRADLE_HOME/bin
RUN gradle --version

# Copy the Gradle wrapper and project files
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle ./
COPY settings.gradle ./

# Copy the application source code
COPY src ./src

# Give execution permissions to the Gradle wrapper
RUN chmod +x ./gradlew

# Build the application
RUN gradle clean build

# Expose the port the application runs on
EXPOSE 8082

# Set the entrypoint for the container to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]