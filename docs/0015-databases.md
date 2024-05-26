# Gentle introduction to relational databases

For many years, relational databases where the only serious way to keep relevant
data saved in a safe and retrievable when needed.

[Some would say][0600] that it still is the only serious way.

Relational databases emerged victorious from alternative ways to store large
volume of enterprise way back in the 80's,
[there is a fine documentary about it][0601].


## Write a program to query data

Kotlin inherits from java a library called [JDBC][0610] and you can use it to
query the database:

```kotlin
// ...
fun list() {
    println("|#\t|description\t|done\t|updated\t")
    connection.prepareStatement(
        """
            select * from todos
        """.trimIndent()
    ).use {
        val rs = it.executeQuery()
        while (rs.next()) {
            println(
                "|${rs.getLong("id")}\t" +
                        "|${rs.getString("description")}\t" +
                        "|${rs.getBoolean("done")}\t" +
                        "|${rs.getTimestamp("updated")}"
            )
        }
    }
}
// ...
```

JDBC isn't famous for the concise, easy to use API, but it's a good base for the
people writing alternative query libraries.

You can check the complete sample code [here][0611].

## Cool database runtimes

When people talk about _databases_, what they really mean is the collection of
software needed to provide database management. [RDBMS][0602] - Relational
Database Management System is the term to kick in to find ou more.

Some of them are [quite humble][0603], just one program saving into a file,
while others are [huge][0604], [state-of-the-art][0605] [beasts][0606], intended
for brutally large data volume processing.

There are [many RDBMS][0607] around, but let's focus on some that will help you
to deliver things fast.

### SQLite

Let's start with the [tiny guy][0603]. They say it's not meant to be used for
serious applications, yet it is easily the most ubiquitous database engine in 
the entire world.

There are [several ways to install][0608] the command line tool and the library,
but we'll focus on how to handle the database using the command line at first,
then we'll sample how a simple kotlin program could consume the data.

Also, we'll make use of [SQL - Structured Query Language][0609] to create the
sample todo list database, but we'll check it in detail further in this chapter.

Open the command line terminal, call the `sqlite3` command you have installed
from previous step and:

```bash
sombriks@zagreus Documents % sqlite3 todos.db
SQLite version 3.43.2 2023-10-10 13:08:14
Enter ".help" for usage hints.
sqlite> create table todos(id integer, description text, updated timestamp);
sqlite> insert into todos(id,description,updated) values(1, 'do the dishes', '2024-05-26');
sqlite> 
```

Hit `CTRL+D` to exit, the file **todos.db** now is there.

You can go back into your database with the same previous command 
`sqlite3 todos.db`. Now try to query the data:

```bash
sombriks@zagreus Documents % sqlite3 todos.db
SQLite version 3.43.2 2023-10-10 13:08:14
Enter ".help" for usage hints.
sqlite> select * from todos;
1|do the dishes|2024-05-26
sqlite> 
```

SQLite figures into this list because it is by far the easiest one to use when
we need a small and simple database for testing purposes. As you can see you
don't even need to setup a real project to test things with it.

### H2

This is another tiny, embeddable database runtime that you can use in your
kotlin/spring projects. 

In fact, [spring boot will provision a h2 embedded instance][0612] if your tests
needs a [DataSource][0613], but you configured none.

### MySQL

MySQL is a popular, robust RDBMS widely used on internet solutions.

It's quite feature complete (I still don't forgive the lack of
[RETURNING statements][0614], i don't care if it's not [ANSI-SQL][0615]) and
easy to find years of documentation about it.

It has a notable fork, [MariaDB][0616] (which [has the RETURNING][0617]).

### PostgreSQL, the open-source and enterprise one

## Cool database clients

## The SQL language

### DDL

#### create table, alter table, drop table

#### create index, create view

#### foreign keys and cascade operations

### DML

#### insert, update, delete

#### select, join, union

#### order by, limit, offset, group by

#### window functions

[0600]: http://www.sarahmei.com/blog/2013/11/11/why-you-should-never-use-mongodb/
[0601]: https://www.youtube.com/watch?v=z8L202FlmD4
[0602]: https://www.oracle.com/database/what-is-a-relational-database/
[0603]: https://www.sqlite.org/index.html
[0604]: https://www.ibm.com/products/db2/
[0605]: https://postgresql.org/
[0606]: https://www.oracle.com/database/features/
[0607]: https://hpi.de/naumann/projects/rdbms-genealogy.html
[0608]: https://www.sqlite.org/download.html
[0609]: https://en.wikipedia.org/wiki/SQL
[0610]: https://docs.oracle.com/javase/tutorial/jdbc/TOC.html
[0611]: ../samples/project-013-simple-databases/README.md
[0612]: https://docs.spring.io/spring-boot/reference/data/sql.html#data.sql.datasource.embedded
[0613]: https://www.digitalocean.com/community/tutorials/java-datasource-jdbc-datasource-example
[0614]: https://stackoverflow.com/a/4734619/420096
[0615]: https://learnsql.com/blog/history-of-sql-standards/#sql-2003-and-beyond
[0616]: https://mariadb.org/
[0617]: https://mariadb.com/kb/en/insertreturning/
