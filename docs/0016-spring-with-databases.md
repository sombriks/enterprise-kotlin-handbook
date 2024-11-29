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
datasource.url=jdbc:h2:./todo
datasource.username=enterprise
```

A very simple configuration using H2, which we used before.

On top of a datasource, several other cool things are built up, like
[query mappers][0705] and [ORM's][0706]


## JPA

### Entities

### EntityManager

## Spring data repositories

## Paging and sorting

## Database Migrations

### Liquibase

[0700]: ./0015-databases.md
[0701]: ../samples/project-013-simple-databases
[0702]: https://docs.oracle.com/javase/tutorial/jdbc/TOC.html
[0703]: https://docs.oracle.com/javase/tutorial/jdbc/basics/sqldatasources.html
[0704]: https://tomcat.apache.org/tomcat-11.0-doc/jndi-resources-howto.html
