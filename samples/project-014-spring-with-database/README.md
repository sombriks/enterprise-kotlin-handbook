# project-014-spring-with-database

Sample project for [chapter 016](../../docs/0016-spring-with-databases.md)

## Dependencies

- java 17
- kotlin 1.9
- maven 3.9
- spring-boot 3.4

## How to build

```bash
./mvnw compile package
```

## How to test

```bash
./mvnw test
```

## How to run

Via maven plugin:

```bash
./mvnw spring-boot:run
```

Running executable jar file:

```bash
java -jar target/project-014-0.0.1-SNAPSHOT.jar
```

## Usage

The application persists the number of site visits at
<http://localhost:8080/visit>. The number of total visits must survive
application restarts as long as the database isn't deleted.
