# A simple kotlin project managed by maven

Small maven project to sample kotlin build, test and so on

## Requirements

- java 17 or newer
- kotlin 1.9 or newer
- maven 3.9 or newer

## How to build

```bash
mvn clean compile package
```

## How to run

```bash
java -jar target/project-007-sample-maven-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## How to test

Tests already run during the build but you can run just them if you want:

```bash
mvn test
```

## Noteworthy

- Don't get overwhelmed by the ugliness of the pom.xml file. Once configured you
  rarely will need to look at that again, except for the dependency section.
- We'll discuss tests and coverage later, this project offers just a sample.
- Nowadays java libraries will tell you the maven coordinates instead of a
  direct download link.
