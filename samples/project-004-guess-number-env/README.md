# Guess the secret number

Now the secret number is configurable via SECRET_NUMBER environment variable!

## How to compile

```bash
kotlinc src/**/*.kt -d build
```

## How to run

```bash
SECRET_NUMBER=11 kotlin -cp build project004.GuessKt 11
```

## Noteworthy

- [System.getenv()][getenv] is a java function being consumed by kotlin code
- If environment variable is not set an [exception][exception] is thrown.

See [docs](../../docs/0011-kotlin-basics.md) for further details.

[getenv]: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/System.html#getenv(java.lang.String)
[exception]: https://docs.oracle.com/javase/tutorial/essential/exceptions/index.html
