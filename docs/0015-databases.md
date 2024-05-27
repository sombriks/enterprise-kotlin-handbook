# Gentle introduction to relational databases

For many years, relational databases where the only serious way to keep relevant
data saved in a safe and retrievable when needed.

[Some would say][0600] that it still is the only serious way.

Relational databases emerged victorious from alternative ways to store large
volume of enterprise way back in the 80's,
[there is a fine documentary about it][0601].

They worth use instead of just shove all the data inside a plain file because
most of them offer [ACID][0622] properties.

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

Now, this is the one you pick when you want to do big, serious business.
It's also one of the oldest RDBMS in activity, dating from the time of
relational database invention.

With postgres you can handle [internet scale][0618] operations, your hardware is
the limit. It is into another league, competing with systems like Oracle,
IBM DB2 and SQL Server.

Pick postgres whenever possible.

## Cool database clients

Most RDBMS offers tools like the sqlite command line tool we used earlier in
this chapter or something like the H2 [embedded web consoles][0619].

But for most database administration procedures something better can be used.

### dbeaver

[dbeaver][0620] supports a wide range of RDBMS. Everything the jvm touches it
touches too.

### Intellij Ultimate or DataGrip

[datagrip][0621] supports a wide range of RDBMS. Everything the jvm touches it
touches too.

## The SQL language

Now let's talk about the way we interact with databases.

The Structured Query Language is the standard way to retrieve, model, modify and
evolve data stored into a RDBMS.

Since the language existed for years before the standardization appeared (and
for decades before that started to be taken seriously), a few
[SQL Dialects][0624] exists in different RDBMS.

It can be divided in several categories, but the principal ones are DDL and DML.

### DDL

DDL stands for Data Definition Language. You use it to describe the data schema,
tables and relations between them. We saw it in action in the
[initDb function][0623] back in the sample project.

#### create table, alter table, drop table

We talk SQL, we talk tables. 

A table is the foundational structure used to model our data. It holds columns,
each column represents a characteristic of the data we want stored.

Let's say we want to keep a list of friends. We need their names.

```sql
create table friends(name text);
```

This table represents exactly what we requested, although it is horribly, poorly
designed.

- If we have two friends with the same name, we end up with no easy way to
  differentiate one from another.
- I don't know when i inserted that friend in my database.
- Extra information about the friend (phone, email, address, for example) would
  be nice to have.

We can recreate our table to fix those problems i just invented:

```sql
-- throw away previous table definition
drop table friends;
-- do it again
create table friends
(
    id      integer primary key,
    name    text      not null,
    created timestamp not null default current_timestamp
);
```

Or, instead of throw it away we just `alter` the table definition:

```sql
alter table friends add column phone text;
alter table friends add column email text;
alter table friends add column address text;
```

Some database engines (SQLite, for example!) won't let you add primary keys via
alter table, this is why the example above is how it is.

What?? what is a primary key and why 9 out of 10 [DBA][0625]s call it id?

##### What is a primary key

Whenever you insert data into a table, a record is created. A record is a 'line'
in the table.

If you insert the same data twice, how to know the difference between records?
They are the same, yet they're not.

The concept of [identity][0626] helps to solve it. You can read the
[theseus ship paradox][0627] to get things even more clear: data is subject to
change over the time, yet each record is supposed to be unique.

#### create index, create view

Indexes are a way to inform the RDBMS that a certain column will be requested by
queries pretty often.

Primary keys are usually indexed automatically.

You can add uniqueness on your column definition and it will also be indexed.

The table bellow defines unique indexes on email and phone. no friend share such
information, but it's ok to have more than one friend with the same address.

```sql
-- drop table friends;
create table friends
(
  id      integer primary key,
  name    text        not null,
  email   text unique not null,
  phone   text unique not null,
  address text        not null,
  created timestamp   not null default current_timestamp
);
```

This is an alternative syntax, the advantage is we _know_ the key name:

```sql
-- drop table friends;
create table friends
(
    id      integer primary key,
    name    text      not null,
    email   text      not null,
    phone   text      not null,
    address text      not null,
    created timestamp not null default current_timestamp,
    constraint friends_uq_email unique (email),
    constraint friends_uq_hone unique (phone)
);
```

This also works for some dialects (H2 and the rest of the world ok, sqlite no):

```sql
-- drop table friends;
create table friends
(
    id      integer primary key,
    name    text      not null,
    email   text      not null,
    phone   text      not null,
    address text      not null,
    created timestamp not null default current_timestamp
);

alter table friends add constraint friends_uq_email unique (email);
alter table friends add constraint friends_uq_hone unique (phone);
```

On the other hand, sometimes we want just a subset of results, or even there are
two or more tables we join very often (see what is a join a little further in
this chapter). For those scenarios we can create views:

```sql
create view friend_emails as select id, email from friends;
```

Those two concepts will make more sense in the next topics.

#### foreign keys

Let's talk about the "Relational" in Relational Databases.

There are relations between the tables. Let's evolve our friends database, let's
have one table for friends, another for addresses because yes.

```sql
drop table friends;

create table addresses
(
    id      integer primary key,
    name    text      not null,
    number  text,
    created timestamp not null default current_timestamp,
    constraint addresses_uq_name_number unique (name, number)
);

create table friends
(
    id           integer primary key,
    name         text        not null,
    email        text unique not null,
    phone        text unique not null,
    addresses_id integer not null,
    created      timestamp   not null default current_timestamp,
    foreign key (addresses_id) references addresses (id)
);
```

The way address is defined allow us to share the same address with more than one
friend; 

But what happens if i delete an address? 

Depending on the database runtime, the delete operation can either be prevented
or it occurs but let you with a database with invalid state.

Databases honoring the [ACID][0622] properties will prevent you from doing that.

### DML

[DML][0628] stands for Data Manipulation language. It differs from what we're
seeing because the first one, DDL, explains how our data should look like. This
one is to feed data into it.

#### insert, update, delete, cascade 

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
[0618]: https://www.percona.com/blog/millions-queries-per-second-postgresql-and-mysql-peaceful-battle-at-modern-demanding-workloads/
[0619]: https://www.h2database.com/html/tutorial.html#tutorial_starting_h2_console
[0620]: https://dbeaver.io/
[0621]: https://www.jetbrains.com/datagrip/
[0622]: https://en.wikipedia.org/wiki/ACID
[0623]: ../samples/project-013-simple-databases/src/project013/QueryDb.kt
[0624]: https://en.wikibooks.org/wiki/SQL_Dialects_Reference/Introduction#SQL_implementations_covered_in_this_book
[0625]: https://en.wikipedia.org/wiki/Database_administration
[0626]: <https://en.wikipedia.org/wiki/Identity_(philosophy)>
[0627]: https://en.wikipedia.org/wiki/Ship_of_Theseus
[0628]: https://en.wikipedia.org/wiki/Data_manipulation_language
