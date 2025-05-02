# Mocks on spring project

Project sampling test scenarios using spring capabilities.

## Requirements

- java 21
- docker 28

## Project setup

Using [spring initializer][initializr], scaffold a project.

Add this extra dependency:

```xml
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>postgresql</artifactId>
    <scope>test</scope>
</dependency>
```

Using docker, spin a postgresql database:

```bash
docker run --rm -d \
  --name pg-database -p 5432:5432 \
  -e POSTGRES_PASSWORD=enterprise \
  -e POSTGRES_USER=enterprise \
  -e POSTGRES_DB=products \
  postgres:16-alpine
```

Modify `TestcontainersConfiguration` to provide a test database:

```kotlin
// ...
@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    fun postgresContainer(): PostgreSQLContainer<*> = PostgreSQLContainer(
        DockerImageName.parse("postgres:16-alpine")
    ).withEnv(
        mapOf(
            "POSTGRES_DB" to "products",
            "POSTGRES_USER" to "enterprise",
            "POSTGRES_PASSWORD" to "enterprise"
        )
    )
}
```

Finally add the [default liquibase configuration][liquibase-changelog] and the
[fist migration][migration]:

```bash
mkdir -p src/main/resources/db/changelog/migrations
touch src/main/resources/db/changelog/migrations/2025-05-01-initial-schema.sql
touch src/main/resources/db/changelog/db.changelog-master.yaml
```

And that's it, the project is

## How to build

```bash
./mvnw package
```

## How to run

```bash
./mvnw springboot:run
```

## How to test

```bash
./mvnw test
```

## Noteworthy

- avoid the `org.junit.Test` annotation, use the new one at 
  `org.junit.jupiter.api.Test`

[initializr]: https://start.spring.io/#!type=maven-project&language=kotlin&platformVersion=3.4.5&packaging=jar&jvmVersion=21&groupId=project015&artifactId=project015&name=project015&description=Demo%20project%20for%20Spring%20Boot&packageName=project015&dependencies=devtools,web,data-jpa,liquibase,testcontainers,postgresql
[liquibase-changelog]: ./src/main/resources/db/changelog/db.changelog-master.yaml
[migtration]: ./src/main/resources/db/changelog/migrations/2025-05-01-initial-schema.sql
