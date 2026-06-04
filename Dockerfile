FROM gradle:8.14-jdk21 AS builder

WORKDIR /app

COPY build.gradle* ./
COPY settings.gradle* ./
COPY gradle ./gradle
COPY gradlew ./

RUN chmod +x gradlew

RUN ./gradlew dependencies --no-daemon || true

COPY src ./src

RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080


ENTRYPOINT ["sh", "-c", "java -Xms128m -Xmx256m -XX:MaxMetaspaceSize=128m -Xss256k -XX:+UseSerialGC -jar app.jar"]