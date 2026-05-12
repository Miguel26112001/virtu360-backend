# =========================================================
# STAGE 1 - BUILD
# =========================================================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom first for dependency caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build application
RUN mvn clean package -DskipTests

# =========================================================
# STAGE 2 - RUNTIME
# =========================================================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Create non-root user
RUN addgroup --system spring && adduser --system spring --ingroup spring

# Copy generated jar
COPY --from=builder /app/target/*.jar app.jar

# Change ownership
RUN chown -R spring:spring /app

USER spring:spring

# Render dynamic port
EXPOSE 8080

# =========================================================
# JVM OPTIMIZATION FOR RENDER FREE TIER
# =========================================================
ENTRYPOINT [
  "java",
  "-Xms128m",
  "-Xmx320m",
  "-XX:+UseSerialGC",
  "-XX:+UseContainerSupport",
  "-XX:MaxMetaspaceSize=128m",
  "-Djava.security.egd=file:/dev/./urandom",
  "-jar",
  "app.jar"
]