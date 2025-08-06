#Use JDK 24 base image
FROM eclipse-temurin:24-jdk

#Set working directory
WORKDIR /app

#Copy the jar into the container
COPY target/primary-backend-0.0.1-SNAPSHOT.jar primary-backend.jar

#Expose port used by the spring boot
EXPOSE 8080

#Run the app
ENTRYPOINT ["java","-jar","primary-backend.jar"]