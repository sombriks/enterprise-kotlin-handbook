# Spring Boot example

This samples [Spring Boot[spring-boot]] (finally!)

It does nothing but it is ready to!

## Dependencies

- java 17
- kotlin 1.9
- gradle 8.5
- spring 3.2

## How to build

```bash
./gradlew build
```

## How to run

```bash
./gradlew bootRun
```

## How to test

```bash
./gradlew test
```

## Noteworthy

- Except for this README, everything else was created via [initializr][init]
- Spring boot is spring, but spring isn't spring boot. See more in [docs][docs].
- in order to add more functionality offered by spring boot, you need to add the
  [starters][starters] as dependencies.

[spring-boot]: https://spring.io/projects/spring-boot
[init]: https://start.spring.io/
[docs]: ../../docs/0014-spring.md
[starters]: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using.build-systems.starters
