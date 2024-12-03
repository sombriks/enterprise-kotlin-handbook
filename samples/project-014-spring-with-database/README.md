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

All saved visits can be seen at <http://localhost:8080/visit/em-list-all>.

Sample on search at <http://localhost:8080/visit/repo-list-by-created?start=2024-01-01T00:00:00Z&end=2030-12-31T23:59:59Z>.

Sample on paging and sorting at <http://localhost:8080/visit/repo-list-paged>.
