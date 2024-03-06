# Spring Boot Web

Project sampling spring boot web. And some tricks with environment and profiles

## Dependencies

- java 17
- kotlin 1.9
- maven 3.9
- spring-boot 3.2

## How to build

```bash
./mvnw compile package
```

## How to test

```bash
./mvnw test
```

## How to run

Via maven plugin:

```bash
./mvnw spring-boot:run
```

Running executable jar file:

```bash
java -jar target/project-012-0.0.1-SNAPSHOT.jar
```

## Run with another profile

You can pass the [-Dspring.profiles.active=special][sb-jvm-profiles] to the jvm
so spring will load the default application.properties AND the profile one:

```bash
java -Dspring.profiles.active=special -jar target/project-012-0.0.1-SNAPSHOT.jar
```

Or pass the [-Dspring-boot.run.profiles=special][sb-mvn-profiles] to maven:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=special
```

This is useful to make your service environment aware.

## Set profile and other properties using environment variable

Another option is to use an environment variable to change current profile.
property values can be overridden in spring-boot using this strategy. The
following override will work well on unix-like systems:

```bash
SPRING_PROFILES_ACTIVE=special MY_MESSAGE="Hello to you!" java -jar target/project-012-0.0.1-SNAPSHOT.jar
```

It works with maven plugin as well:

```bash
SPRING_PROFILES_ACTIVE=special MY_MESSAGE="Good day sir!" ./mvnw spring-boot:run
```

Environment variables are the preferred way to tweak configurations in
production mode.

## Noteworthy

- This project was created using the spring [initializr][start] and a few
  [properties][props-maven] [added][props-kotlin] to pom.xml file.
- Name your profiles with simple, meaningful names.
- It is possible to load more than once extra profile, just provide a
  coma-separated list with their names.
- It's possible to set active profiles from code, see [the test cases][tests].
- As **exercise**, figure out how to make one extra profile get loaded by
  default.

[start]: https://start.spring.io/
[props-maven]: https://maven.apache.org/plugins/maven-compiler-plugin/examples/set-compiler-source-and-target.html
[props-kotlin]: https://kotlinlang.org/docs/maven.html#attributes-specific-to-jvm
[sb-mvn-profiles]: https://docs.spring.io/spring-boot/docs/2.0.1.RELEASE/maven-plugin/examples/run-profiles.html
[sb-jvm-profiles]: https://docs.spring.io/spring-boot/docs/1.2.0.M1/reference/html/boot-features-profiles.html
[tests]: ./src/test/kotlin/project012/RestControllerTest.kt
