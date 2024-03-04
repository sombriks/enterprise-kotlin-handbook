# Unit tests with kotlin

Using [JUnit][junit].

## Dependencies

## How to build

```bash
gradle build
```

## How to run

```bash
gradle run
```

## How to test

```bash
gradle test
```

## Noteworthy

- This is a simpler gradle setup compared to the [first example][gradle].
- Unlike kotlin script (.kts) it uses default [groovy][groovy] scripting for
  project configuration. I'ts quite similar to .kts scripts but be aware of the differences.
- There is no wrapper, no specified java version, therefore the build becomes
  dependent of the build machine. Fix that as an exercise.
- The code convention in kotlin is _CamelCase_, following pretty much what java
  project does. It's not mandatory, as you can see in the _jazz_ on files in
  these example projects. But follow the code conventions, they help you to
  create unexciting, boring and previsible projects.
- Hamcrest and Mockito are pretty common libraries used with junit, so they are
  sampled here. Some functions those libraries have are reserved words in kotlin
  and must be escaped with backticks (`) to work as expected.

[junit]: https://junit.org/junit5/docs/current/user-guide/#overview-getting-started
[gradle]: ../project-008-sample-gradle/README.md
[groovy]: https://groovy-lang.org/
