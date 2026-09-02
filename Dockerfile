FROM amazoncorretto:25-alpine-jdk

WORKDIR /app

# Copy the jar file
COPY target/register-*.jar app.jar

# Expose port
EXPOSE 19090

# Run the application
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
