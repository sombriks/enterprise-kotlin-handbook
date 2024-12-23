# Connecting and managing databases from a spring/kotlin application

After [the SQL marathon][0700] we just ran, it's time to assume we know
everything about data modeling and let's dive on how to use a database with
spring boot.

## Datasource configuration

In the [last sample project][0701], we used [JDBC][0702] to access the database.
Several API's are built on top of that in order to ease things for enterprise
development. For instance, a [Datasource][0703] helps to provide connections
without explicit knowledge of connection details. [JEE technologies][0704] have
sophisticated ways to acquire and release connections.

Spring boot however is meant to make things easier so you don't have to deal
with any of those complexities. Here's a sample datasource configuration:

```properties
spring.datasource.url=jdbc:h2:./visits
spring.datasource.username=enterprise
```

A very simple configuration using H2, which we used before. All available data
options can be checked [here][0707].

On top of a datasource, several other cool things are built up, like
[query mappers][0705] and [ORM's][0706]. Enterprise java has a specification for
ORM called JPA and it's very popular due the delivered productivity and spring
boot offers even easier access to it.

## JPA

The Java Persistence API is another abstraction layer on top of not knowing deep
details about the database being consumed. You may ask why is that important,
since everyone uses PostgreSQL, but it was tricky in the past due the wide
number of [SQL Dialects][0708].

The key idea behind any ORM is to map entities between the database and the
application. Usually the application has structs or classes while the database
has tables and views. But in the end they are mostly the same information.

### Entities

An [Entity][0709] is a class decorated with the @Entity annotation. It means
that the fields in that class, more or less, map to columns in a table with
similar name. Here's a sample from the [sample project][0716]:

```kotlin
package sample.project14

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Visit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Temporal(TemporalType.TIMESTAMP)
    var created: LocalDateTime? = LocalDateTime.now(),
)
```

This entity expects a table called *visit* containing two columns: *id* and
*created*. It also expects the database handles [primary key generation][0710]
and the type of *created* column to be a timestamp. Besides that, there is a lot
that JPA hides by default so we don't need to think about proper queries all the
time.

That approach, of course, has [some criticism][0711] but we won't dive into it.

### EntityManager

By the current JEE standards, the proper way to handle database access is using
an [EntityManager][0712]. It queries your mapped entity classes:

```kotlin
package sample.project14

import jakarta.persistence.EntityManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("visit")
class Controller(val em: EntityManager) {

  @GetMapping("count")
  fun visit(): List<Visit> = em.createQuery("select v from Visit v", Visit::class.java).resultList

  // ...
}
```

No need to know any SQL or its dialects, but a language called [JPQL][0713].

## Spring data repositories

Then Spring make things even easier with [Repositories][0714]. With repository,
common CRUD operations and more are available for free. You just need to extend
one interface, nothing more:

```kotlin
package sample.project14

import org.springframework.data.jpa.repository.JpaRepository

interface VisitRepository : JpaRepository<Visit, Long?>
```

Then you can save and count (and [more][0715]) like this, zero SQL queries:

```kotlin
package sample.project14

import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("visit")
class Controller(
    val em: EntityManager,
    val repo: VisitRepository
) {

  // ...

  @GetMapping
  @Transactional
  fun count(): Long {
      repo.save(Visit())
      return repo.count()
  }

  // ...
}
```

## Listing, paging and sorting with repositories

Basic listing can be achieved with **findAll**, it's already there like **save**
and **count**. Needless to say to not use **findAll** on huge tables.

Repositories even has that special feature that let we create new methods in the
interface with special names and then it understands the desired query. For
example, **findByCreatedBetween** will take two LocalDateTime arguments and then
search visits by the created field:

```kotlin
package sample.project14

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface VisitRepository : JpaRepository<Visit, Long?> {

    fun findByCreatedBetween(createdAfter: LocalDateTime, createdBefore: LocalDateTime): List<Visit>
}
```

Another feature very important elegantly provided by repositories is the
[pagination][0717]. Again, all we need to use is a special method signature,
receiving a [Pageable][0718] and returning either a List or a [Page][0719]:

```kotlin
package sample.project14

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

// ...

@RestController
@RequestMapping("visit")
class Controller(
    val em: EntityManager,
    val repo: VisitRepository,
) {

    // ...

    @GetMapping("repo-list-paged")
    fun repoListPaged(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): Page<Visit> = repo.listPaged(
        PageRequest.of(
            page, size,
            Sort.Direction.DESC, "id"
        )
    )
}
```

## Database Migrations

Finally, let's talk about migrations.

Any actively used system is constantly evolving and so the infrastructure behind
it. That means changes in our code, our database, our user interfaces and so on.

Changes in the database had a history of be hard to track, but the migrations
pattern defines a good way to both track the changes and set a
[source of truth][0720] to them.

### Liquibase

There are several migrate systems out there, [liquibase][0721] is one of the
best and it happens to be supported by spring boot out of the box. All you have
to do is to add [liquibase-core][0722] as dependency and to make sure a
liquibase **root** [changelog][0723] is present either in the default location
or at the configured path in your application.properties:

```properties
spring.application.name=project-014-spring-with-database
spring.datasource.url=jdbc:h2:./visits
spring.datasource.username=enterprise
#spring.liquibase.change-log=classpath:/db/changelog/db.changelog-master.yaml
spring.liquibase.change-log=classpath:/changelog-master.yml

```

By this configuration, we need to create a file called **changelog-master.yml**
and place it in the same folder (resources probably) the
**application.properties** file:

```yaml
databaseChangeLog:
  - includeAll:
      path: migrations
      relativeToChangelogFile: true
```

And this file expects a folder called *migrations*. There we place our migrate
files in any expected [migrate format supported by liquibase][0724]:

```sql
-- liquibase formatted sql
-- changeset sombriks:2024-12-03-create-table-visit.sql

create table visit(
    id identity primary key,
    created timestamp
);
```

A migrate/changelog file can contain one or more changesets. For each changeset,
it may or may not contain a [rollback][0725].

Now all our database changes can be put in migrations and tracked along changes
in the codebase.

That's it, spring/kotlin applications are ready for serious business. Remember
to check the [sample project for this chapter][0701].

Next we will discuss [more tests][0726].

[0700]: ./0015-databases.md
[0701]: ../samples/project-013-simple-databases
[0702]: <https://docs.oracle.com/javase/tutorial/jdbc/TOC.html>
[0703]: <https://docs.oracle.com/javase/tutorial/jdbc/basics/sqldatasources.html>
[0704]: <https://tomcat.apache.org/tomcat-11.0-doc/jndi-resources-howto.html>
[0705]: <https://jdbi.org/>
[0706]: <https://jakarta.ee/learn/docs/jakartaee-tutorial/current/persist/persistence-intro/persistence-intro.html>
[0707]: <https://docs.spring.io/spring-boot/appendix/application-properties/index.html#appendix.application-properties.data>
[0708]: <https://learnsql.com/blog/what-sql-dialect-to-learn/>
[0709]: <https://spring.io/guides/gs/accessing-data-jpa>
[0710]: <https://learnsql.com/blog/what-is-a-primary-key/>
[0711]: <https://blog.codinghorror.com/object-relational-mapping-is-the-vietnam-of-computer-science/>
[0712]: <https://jakartaee.github.io/persistence/latest-nightly/api/jakarta.persistence/jakarta/persistence/EntityManager.html>
[0713]: <https://jakarta.ee/learn/docs/jakartaee-tutorial/current/persist/persistence-querylanguage/persistence-querylanguage.html#_creating_queries_using_the_jakarta_persistence_query_language>
[0714]: <https://spring.io/guides/gs/accessing-data-jpa>
[0715]: <https://docs.spring.io/spring-data/jpa/reference/repositories/query-methods-details.html#repositories.query-methods.query-creation>
[0716]: ../samples/project-014-spring-with-database/
[0717]: <https://www.baeldung.com/spring-data-jpa-pagination-sorting>
[0718]: <https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html>
[0719]: <https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html>
[0720]: <https://en.wikipedia.org/wiki/Single_source_of_truth>
[0721]: <https://www.liquibase.com/>
[0722]: <https://central.sonatype.com/artifact/org.liquibase/liquibase-core>
[0723]: <https://docs.liquibase.com/concepts/changelogs/home.html>
[0724]: https://docs.liquibase.com/concepts/changelogs/sql-format.html
[0725]: https://docs.liquibase.com/workflows/liquibase-community/automatic-custom-rollbacks.html
[0726]: ./0017-unit-tests-part-2.md
