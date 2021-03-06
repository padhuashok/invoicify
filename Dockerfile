# Docker file for two phase build
# Phase 1 - Build the application .jar file and name it builder
FROM openjdk:11.0-jdk-slim as builder
VOLUME /tmp
COPY . .
RUN chmod +x gradlew
RUN ./gradlew build

# Phase 2 - Build container with runtime only to use .jar file within
FROM openjdk:11.0-jre-slim
WORKDIR /app
# Copy .jar file (aka, builder)
COPY --from=builder build/libs/invoicify-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Xmx300m",  "-Xss512k", "-jar", "app.jar"]


