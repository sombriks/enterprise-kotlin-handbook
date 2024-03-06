# Minimal spring example

Small project demonstrating dependency inversion/injection capabilities with
bare-bones spring.

## Dependencies

- maven 3.9 or newer
- java 17 or newer
- spring (just core, context and test) 6.1 or newer

## How to build

```bash
./mvnw compile package
```

## How to test

```bash
./mvnw test
```

## How to run

```bash
./mvnw compile exec:java
```

## Noteworthy

- Conversion of this project into a kotlin project is left as **exercise**.
- The jar assembly plugin configuration is left as **exercise**.
- Spring core and context dependencies are the minimum, bare-bones to sample the
  container and dependency injection.
- Properties is the standard way to inject configurations.
- Spring test offers some facilities to ease the component testing process.
