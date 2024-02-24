# Guess until you get it right

This example keeps asking the user for a number until the number is the correct
one.

## How to build

```bash
kotlinc src/**/*.kt -d build
```

## How to run

```bash
kotlin -cp build project005.GuessKt
```

## Noteworthy

- In this example we use another java class for the sake of the example: Scanner
- We had to `import` that class, since that one isn't from
  [the java.lang package][java-lang].
- Scanner deals with [closeable resources][closeable], so we can use the
  [use][use-resource] kotlin extension function.
- When we created the Scanner, we passed **System.`in`** as parameter. The
  backticks are escaping the `in` because it's a reserved word in kotlin but not
  in java. Kotlin also permits [special identifiers][backtick] using backticks
  in tests.

See [docs](../../docs/0011-kotlin-basics.md) for further details.

[java-lang]: https://docs.oracle.com/javase/8/docs/api/java/lang/package-summary.html
[closeable]: <https://docs.oracle.com/javase/8/docs/api/java/io/Closeable.html>
[use-resource]: <https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/use.html>
[backtick]: https://kotlinlang.org/docs/coding-conventions.html#names-for-test-methods
