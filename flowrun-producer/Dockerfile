#Use JDK 24 base image
FROM eclipse-temurin:24-jdk

#Set working directory
WORKDIR /app

#Copy the jar into the container
COPY target/flowrun-producer-0.0.1-SNAPSHOT.jar flowrun-producer.jar

#Expose port used by the spring boot
EXPOSE 8082

#Run the app
ENTRYPOINT ["java","-jar","flowrun-producer.jar"]