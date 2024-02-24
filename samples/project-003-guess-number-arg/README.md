# Guess the secret number

The secret number is 7, but don't spoil the surprise, try it from command line!

## Requirements

- java virtual machine 17 or newer properly installed
- kotlin compiler 1.9 or newer available in the command line

## How to compile

```bash
kotlinc src/**/*.kt -d build
```

## How to run

```bash
kotlin -cp build project003.GuessKt 5
```

## Noteworthy

- [Previous][prev1] [Projects][prev2] where messy, having sources and results in
  the same folder. In this one we introduce a `src` folder.
- Mind the proper package calculation: although the path to sources is
  `src/project003/Guess.kt`, the **declared package in source file** is what
  really counts.
- [Classpath][cp] is a concept inherited from the JVM.

See [docs](../../docs/0011-kotlin-basics.md) for further details.

[prev1]: ../project-001-hello-world/README.md
[prev2]: ../project-002-files-and-packages/README.md
[cp]: https://docs.oracle.com/javase/8/docs/technotes/tools/windows/classpath.html#JSWOR581
