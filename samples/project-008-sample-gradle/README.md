# Sample gradle project

Created [from command line][projects] to showcase how a simple gradle project
looks like.

```bash
mkdir project-008-sample-gradle
cd project-008-sample-gradle
echo "no"| gradle init \
  --type kotlin-application \
  --dsl kotlin \
  --test-framework kotlintest \
  --package project008 \
  --project-name project-008-sample-gradle \
  --no-split-project  \
  --java-version 17
```

## Requirements

- java 17
- gradle 8.6

It does not care about your kotlin compiler available at command line. Kotlin is
provisioned as a dependency and plugin for now on.

## How to build

```bash
gradle build 
# or `gradle app:build` # or even `cd app ; gradle build`
```

## How to run

```bash
gradle app:run
```

## How to test

```bash
gradle test
```

## Noteworthy

- The project can be simpler, much similar to the maven structure
- Gradle originally used [groovy][groovy] as dsl language for the `build.gradle`
  file. It supports kotlin in `build.gradle.kts` file nowadays.
- Gradle's [application plugin][app-plugin] creates distributions instead of one
  huge executable jar file, like maven assembly plugin does. Both are valid ways
  to distribute software in the jvm ecosystem.

[projects]: ../../docs/0012-project-setup-with-maven-and-gradle.md
[groovy]: https://groovy-lang.org/
[app-plugin]: https://docs.gradle.org/current/userguide/application_plugin.html
