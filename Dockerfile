# Use Maven to build the application inside the container
FROM maven:3.8.6-eclipse-temurin AS builder

WORKDIR /app

# Copy the entire project
COPY . .

# Build the JAR inside the container
RUN mvn clean package

# Use a lightweight JDK image for running the JAR
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/receipt-processor-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
