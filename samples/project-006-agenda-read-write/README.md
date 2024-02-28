# Simple agenda on a file

Project to exercise basic I/O with kotlin

## How to build

```bash
kotlinc src/**/*.kt -include-runtime -d cmd-agenda.jar
```

## How to run

```bash
java -jar cmd-agenda.jar
```

## Noteworthy

- You can instruct the kotlin compiler to build a [runnable jar file][jar]
  instead of stop the build with intermediate classes. The jar files are the
  standard way of share java compiled code, and so it is for kotlin.
- Method [forEachLine][for-each] is a kotlin extension to the `Paths` java
  utility class. Check the [stdlib][stdlib] for more useful functions.

See [docs](../../docs/0011-kotlin-basics.md) for further details.

[jar]: https://docs.oracle.com/javase/8/docs/technotes/guides/jar/jarGuide.html
[for-each]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-file/for-each-line.html
[stdlib]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io.path/java.nio.file.-path/create-file.html
