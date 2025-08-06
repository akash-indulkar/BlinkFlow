#Use JDK 24 base image
FROM eclipse-temurin:24-jdk

#Set working directory
WORKDIR /app

#Copy the jar into the container
COPY target/flowrun-listener-0.0.1-SNAPSHOT.jar flowrun-listener.jar

#Expose port used by the spring boot
EXPOSE 8081

#Run the app
ENTRYPOINT ["java","-jar","flowrun-listener.jar"]