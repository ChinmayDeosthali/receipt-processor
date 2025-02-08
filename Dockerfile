# Use an official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/receipt-processor-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (8080 by default for Spring Boot)
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
