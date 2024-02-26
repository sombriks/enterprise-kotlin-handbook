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

See [docs](../../docs/0011-kotlin-basics.md) for further details.

[jar]: https://docs.oracle.com/javase/8/docs/technotes/guides/jar/jarGuide.html
