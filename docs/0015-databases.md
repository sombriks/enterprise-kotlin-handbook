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

### Dbeaver

[dbeaver][0620] supports a wide range of RDBMS. Everything the jvm touches it
touches too.

### Intellij Ultimate or DataGrip

[datagrip][0621] supports a wide range of RDBMS. Everything the jvm touches it
touches too.

### DbBrowser for SQLite

[dbbrowser][0633] is a dedicated client for SQLite and it deserves a place here
because SQLite is the one we pick to do fast, _expendable_ test databases.

Sure, command line is cool but sometimes extra tools allows you to go early to
home.

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

#### Create table, alter table, drop table

We talk SQL, we talk tables.

A table is the foundational structure used to model our data. It holds columns,
each column represents a characteristic of the data we want stored.

Let's say we want to keep a list of friends. We need their names.

```sql
create table friends
(
    name text
);
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
alter table friends
    add column phone text;
alter table friends
    add column email text;
alter table friends
    add column address text;
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

##### 'Natural' vs 'artificial' keys

Another common debate is whether to adopt 'natural' or 'artificial' keys.

By natural understand some trait that already exists along the characteristics
of the [entity][0634] being represented by a table.

A social security number could be considered a natural key.

The `ìd` column is an artificial key. It makes sense in the table context but is
completely alien to a person in the real world.

In most cases, **prefer artificial keys**. Natural ones are subject to change
due real world events and thus outside of the RDBMS control.

#### Create index, create view

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

alter table friends
    add constraint friends_uq_email unique (email);
alter table friends
    add constraint friends_uq_hone unique (phone);
```

On the other hand, sometimes we want just a subset of results, or even there are
two or more tables we join very often (see what is a join a little further in
this chapter). For those scenarios we can create views:

```sql
create view friend_emails as
select id, email
from friends;
```

Those two concepts will make more sense in the next topics.

#### Foreign keys

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
    addresses_id integer     not null,
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

But you can solve it explaining what to do in such cases:

```sql
create table friends
(
    id           integer primary key,
    name         text        not null,
    email        text unique not null,
    phone        text unique not null,
    addresses_id integer     not null,
    created      timestamp   not null default current_timestamp,
    foreign key (addresses_id) references addresses (id) on delete set null
);
```

We'll come back to thins in upcoming topics.

### DML

[DML][0628] stands for Data Manipulation language. It differs from what we're
seeing because the first one, DDL, explains how our data should look like. This
one is to feed data into it.

#### Insert

To add data to you database you must use [insert][0629] statements.

For this table (tested on sqlite, _trust me_):

```sql
-- drop table if exists friends;
create table friends
(
    id      integer primary key,
    name    text      not null,
    email   text,
    created timestamp not null default current_timestamp
);
```

You can insert data like this:

```sql
insert into friends(name)
values ('Joe');
```

The output should be something like this:

```bash
1 row affected in 5 ms
```

And you can check your data with a [select][0630] (more on that later):

```sql
select *
from friends;
```

And the result would be:

| id | name | email | created             |
|----|------|-------|---------------------|
| 1  | Joe  | null  | 2024-05-28 11:46:18 |

Not that insert didn't had any info about `id` or `created`columns, but there is
values on them. This is how the column definitions work, you can define keys,
default values, restrictions, you name it (as long as supported by the database
engine in use).

For the following table:

```sql
-- drop table if exists friends;
create table friends
(
    id      integer primary key,
    name    text      not null,
    email   text      not null,
    created timestamp not null default current_timestamp
);
```

The same insert that served us well will fail:

```sql
insert into friends(name)
values ('Joe');
```

With a message error more or less like this:

```console
[2024-05-28 09:02:09] [19] [SQLITE_CONSTRAINT_NOTNULL] A NOT NULL constraint failed (NOT NULL constraint failed: friends.email)
```

what does it mean? it means, according to our table definition, that i don't
accept friends who doesn't have an email.

You fix that insert by simply providing an email:

```sql
insert into friends(name, email)
values ('Joe', 'email@joe.com');
```

It's possible to provide several friends in a single insert:

```sql
insert into friends(name, email)
values ('Joe', 'email@joe.com'),
       ('Anne', 'anne@ggg.co');
```

You can specify values for columns with default values if you want to:

```sql
insert into friends(name, email, created)
values ('Joe', 'email@joe.com', '2024-05-28');
```

But beware! Primary and unique keys will complain about duplicate values and
your insert will fail. The psichology id thing, remember?

```sql
insert into friends(id, name, email)
values (1, 'Joe', 'email@joe.com'),
       (1, 'Anne', 'anne@ggg.co');
```

Expect something like this:

```console
[2024-05-28 09:13:28] [19] [SQLITE_CONSTRAINT_PRIMARYKEY] A PRIMARY KEY constraint failed (UNIQUE constraint failed: friends.id)
```

A funny one, if every column has a default value:

```sql
create table my_messages
(
    id      integer primary key,
    message varchar(255) not null default 'yo!',
    created timestamp    not null default current_timestamp
);
```

You can insert data like this:

```sql
insert into my_messages default
values;
```

Tables with foreign keys marked as a not null restriction will perform extra
checks in your insert attempts.

For this [table schema][0631]:

```sql
create table orders
(
    id      integer   not null primary key,
    created timestamp not null default current_timestamp
);

create table items
(
    id        integer   not null primary key,
    product   text      not null,
    amount    integer   not null default 1,
    orders_id integer   not null references orders (id),
    created   timestamp not null default current_timestamp
);
```

This insert alone will fail (except in sqlite, unless you [enable][0632] the
foreign key check):

```sql
-- pragma foreign_keys = 1;
insert into items (product, orders_id)
values ('cell phone', 10);
```

Resulting error looks like this:

```console
[2024-05-28 10:00:57] [19] [SQLITE_CONSTRAINT_FOREIGNKEY] A foreign key constraint failed (FOREIGN KEY constraint failed)
```

Avoid that by inserting an order first:

```sql
insert into orders default
values returning *
```

The returning part (which MySQL doesn't have but everyone else, even sqlite)
helps to know the newly created record. Here`s a sample output:

| id | created             |
|----|---------------------|
| 1  | 2024-05-28 13:05:48 |

Now the item insert will pass -- as long as the orders_id exists as id in the
order table:

```sql
-- pragma foreign_keys = 1;
insert into items (product, orders_id)
values ('bicycle', 1);
```

#### Update, delete, cascade

Similar to the table schema itself, stored data must evolve too.

Either by modifying or removing records. For example, given this table:

```sql
create table foods
(
    id          integer   not null primary key,
    description text      not null,
    is_sweet    boolean   not null default false,
    created     timestamp not null default current_timestamp
);
```

And this data:

```sql
insert into foods(description)
values ('noodles'),
       ('milky ''n honey chocolate'),
       ('lemon'),
       ('steak');
```

By checking the content we'll notice an error:

```sql
select *
from foods;
```

| id | description              | is_sweet | created                    |
|----|--------------------------|----------|----------------------------|
| 5  | noodles                  | false    | 2024-05-28 23:42:08.814539 | 
| 6  | milky 'n honey chocolate | false    | 2024-05-28 23:42:08.814539 |
| 7  | lemon                    | false    | 2024-05-28 23:42:08.814539 |
| 8  | steak                    | false    | 2024-05-28 23:42:08.814539 | 

The chocolate thing is supposed to be sweet.

To fix that, `update` the row:

```sql
update foods
set is_sweet = true
where description = 'milky ''n honey chocolate';
```

But the name is too difficult to use, and what is that '' thing?

You can use an alternative update:

```sql
update foods
set is_sweet = true
where description like '%honey%';
```

The [like operator][0635] looks for similarities to a given special string.

But the most safe, straightforward way to update a record is to knowing its id:

```sql
update foods
set is_sweet = true
where id = 6;
```

We started to use the [where clause][0636] in our queries, more on that up next.

If we need to get rid of some data, the delete can be used:

```sql
delete
from foods
where id = 6;
```

Note that, like update, a where clause should be used to specify which records
you want to affect. On both scenarios, if no where clause is provided, it will
affect **all records** in the table, which is undesirable on most cases.

Deletes also take relations into account. Given this schema:

```sql
create table team
(
    id identity not null primary key,
    description text not null
);

create table member
(
    id identity not null primary key,
    team_id integer not null,
    name    text    not null,
    constraint fk_member_team_id foreign key (team_id) references team (id)
);

insert into team (description)
values ('team 1'),
       ('team 2');

insert into member(name, team_id)
values ('Joe', 1),
       ('Anna', 1),
       ('Lucas', 1),
       ('Ana', 2),
       ('Kelly', 2);
```

An attempt to delete _team 2_ will likely fail (tested on H2):

```sql
delete
from team
where id = 2;
```

With an output like this:

```console
[2024-05-28 21:12:15] [23503][23503] Referential integrity constraint violation: "FK_MEMBER_TEAM_ID: PUBLIC.MEMBER FOREIGN KEY(TEAM_ID) REFERENCES PUBLIC.TEAM(ID) (2)"; SQL statement:
[2024-05-28 21:12:15] delete from team where id = 2 [23503-220]
```

Yes, yes, you can delete all members where team_id = 2 and then perform the
deletion on team, but you can define a cascade into team_id colum:

```sql
alter table member drop constraint fk_member_team_id;
alter table member add constraint fk_member_team_id foreign key (team_id) references team(id) on delete cascade;
```

Now your deletion goes even better than expected:

```console
TODOS.H2.DB.PUBLIC> delete from team where id = 2
[2024-05-28 21:26:35] 1 row affected in 2 ms
```

Check what's left in the database:

```sql
select * from team join member on member.team_id = team.id;
```

| team.id | description | member.id | team_id | name  | 
|---------|-------------|-----------|---------|-------|
| 1       | team 1      | 1         | 1       | Joe   |
| 1       | team 1      | 2         | 1       | Anna  |
| 1       | team 1      | 3         | 1       | Lucas |



#### Select, join, union

We're using selects for a while in this chapter now but let's look at them in
more detail now.

- We already saw how to define our database (create table, alter, etc.)
- We saw how to feed and modify data into our database (insert, update, delete)
- Now we will see how to ask things to the database and get answers

To perform a select, you need very little, really. Just a RDBMS running and
accepting connections (SQLite is a file, H2, MySQL, PostgreSQL, Oracle and so on
works usually as a client/server application). This is a valid select:

```sql
select 1;
```

No tables. Just a number. Works.

You can select arbitrary values:

```sql
select 'Hi mom', 123, 
       current_timestamp, 
       lower('DON''T YELL AT ME!'), 
       true;
```

When selecting you can mix things with data from your table:

```sql
select concat('the team member ', name, ' from team ', description) as headline
from team join member on member.team_id = team.id;
```

#### Order by, limit, offset

#### Sum, avg, count, group by

#### Window functions

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

[0629]: https://learnsql.com/blog/sql-insert/

[0630]: https://learnsql.com/blog/write-select-statement-sql/

[0631]: https://www.simplilearn.com/tutorials/sql-tutorial/schema-in-sql

[0632]: https://www.sqlite.org/foreignkeys.html

[0633]: https://sqlitebrowser.org/

[0634]: https://en.wikipedia.org/wiki/Entity%E2%80%93relationship_model

[0635]: https://learnsql.com/blog/like-sql-not-like/

[0636]: https://sqldocs.org/sqlite/sqlite-where-clause/
