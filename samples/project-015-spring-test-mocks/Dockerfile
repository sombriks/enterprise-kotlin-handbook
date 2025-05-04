FROM eclipse-temurin:21-jdk-alpine AS builder
ADD src /app/src/
ADD .mvn /app/.mvn/
ADD pom.xml mvnw /app/
WORKDIR /app
RUN ./mvnw package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre-alpine
COPY --from=builder /app/target/project015-0.0.1-SNAPSHOT.jar /app/boot.jar
WORKDIR /app
ENTRYPOINT java -jar boot.jar
