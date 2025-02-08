FROM openjdk:17-jdk-slim

# Set timezone to Asia/Kuala_Lumpur
ENV TZ=Asia/Kuala_Lumpur
RUN apt-get update && \
    apt-get install -y tzdata && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copy the built jar file into the container
COPY target/management-system-0.0.1-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
