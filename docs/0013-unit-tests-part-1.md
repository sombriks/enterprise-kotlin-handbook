# Introduction to JUnit and friends

Like we saw in the previous [chapter][0400], several tools available for kotlin
development where inherited from java ecosystem. It's not different for testing
tools.

The notable one is JUnit.

We're not discussing the merit of write or don't write tests here, i am just
assuming that the need of testable, previsible code is already stablished.

## Project setup

Either have a [maven][0401] or [gradle][0402] project and add the [junit][0403]
to the project config file.

For maven:

```xml
<!-- this is inside the dependencies section in pom.xml -->
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter</artifactId>
  <version>5.10.2</version>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test-junit</artifactId>
  <version>1.9.22</version>
  <scope>test</scope>
</dependency>
```

For gradle:

```groovy
// this is inside the dependencies section in build.gradle.kts
testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.2")
testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.22")
```

Then, following the standard folder layout for maven and gradle projects, you
can write tests inside the `src/test/kotlin` folder.

### Test structure in a nutshell

A test suite is a collection of test cases.

In ~~java~~ kotlin, class methods annotated with @Test is a test case.

## Popular Plugins

### Hamcrest

### Mockito

[0400]: ./0012-project-setup-with-maven-and-gradle.md
[0401]: ../samples/project-007-sample-maven/README.md
[0402]: ../samples/project-008-sample-gradle/README.md
[0403]: https://junit.org/junit5/docs/current/user-guide/#overview-getting-started
