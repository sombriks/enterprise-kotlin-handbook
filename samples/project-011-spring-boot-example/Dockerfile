FROM gradle:jdk21 as BUILDER
ADD src /app/src/
ADD build.gradle.kts settings.gradle.kts /app/
WORKDIR /app
RUN gradle build

FROM eclipse-temurin:21-jre-alpine
COPY --from=BUILDER /app/build/libs/project-011-spring-boot-example-0.0.1-SNAPSHOT.jar /app/boot.jar
WORKDIR /app
ENTRYPOINT java -jar boot.jar