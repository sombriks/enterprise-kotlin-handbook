# Gentle introduction to relational databases

For many years, relational databases where the only serious way to keep relevant
data saved in a safe and retrievable way, whenever needed.

[Some would say][0600] that it still is the only serious and safe way.

Relational databases emerged victorious from alternative ways to store large
volume of enterprise data way back in the 80's,
[there is a fine documentary about it][0601].

They worth to use instead of just shove all the data inside a plain file because
most of them offer [ACID][0622] properties:

1. Atomic operations
1. Consistency of data
1. Isolation in concurrent scenarios
1. Durability of stored data

We'll look on several relevant details in this chapter, but let's start with the
sample code: in fact, i will present things first and explain later, deal with
database management in most enterprise scenario is a more business-related job
than a purely technical challenge: what really matters is _what_ does this data
means instead of _how_ to store it.

## Write a program to query data

This is a small example on _how_ store things.

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
people writing alternative database interfacing libraries.

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
but we'll focus on how to handle the database using the command line, since we
[already sampled][0611] how a kotlin program could consume the data.

Also, we'll make use of [SQL - Structured Query Language][0609] to create the
sample todo list database. More about it, in details, further in this chapter.

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

There is a lot to explain, yet _look how simple is that_!

SQLite figures into this list because it is by far the easiest one to use when
we need a small and simple database for testing purposes. As you can see you
don't even need to setup a real project to test things with it.

### H2

This is another tiny, embeddable, database runtime that you can use in your
kotlin/spring projects.

In fact, [spring boot will provision a h2 embedded instance][0612] if your tests
needs a [DataSource][0613], but you configured no such thing.

Instead of a command line tool, H2 offers a
[feature-complete web admin console][0637], so you get a better visualization of
the data

### MySQL

MySQL is a popular, robust RDBMS widely used on internet solutions.

It's quite feature complete (I still don't forgive the lack of
[RETURNING statements][0614], i don't care if it's not [ANSI-SQL][0615]) and
easy to find years of documentation about it.

It has a notable fork, [MariaDB][0616] (which [has the RETURNING][0617]).

### PostgreSQL, the open-source and enterprise one

Now, this is the one you pick when you want to do big, serious business.
It's also one of the oldest RDBMS in activity, dating from the time of
relational database invention (i mean WARS!!).

With postgres you can handle [internet scale][0618] operations, your hardware is
the limit. It is into another league, competing with systems like Oracle,
IBM DB2 and SQL Server. And winning.

Pick postgres whenever possible.

## Cool database clients

Most RDBMS offers tools like the sqlite command line tool we used earlier in
this chapter or something like the H2 [embedded web consoles][0619].

But if we keep using only the official tool for each RDBMS, we'll end up
overwhelmed with so much tooling to learn about something that is a
[ISO standard][0638].

There are tools able to deal with several distinct database runtimes, so you can
learn one single tool and get the job done even though you don't understand the
database in use completely.

### Dbeaver

[dbeaver][0620] supports a wide range of RDBMS. Everything the jvm touches it
touches too.

### Intellij Ultimate or DataGrip

[datagrip][0621] supports a wide range of RDBMS. Everything the jvm touches it
touches too.

### DbBrowser for SQLite

[dbbrowser][0633] is a dedicated client for SQLite and it deserves a place here
because SQLite is the one we pick to do fast, _expendable_ test databases.

Sure, command line is cool but sometimes visual tools allows you to go early to
home.

## The SQL language

Now let's talk about the way we interact with databases.

The [Structured Query Language][0639] is the standard way to retrieve, model,
modify and evolve data stored into a RDBMS.

Since the language existed for years before the standardization appeared (and
for decades before that standardization started to be taken seriously), a few
[SQL Dialects][0624] exists in different RDBMS. We'll face them up ahead.

It can be divided in several categories, but the principal ones are DDL and DML.

### DDL

DDL stands for Data Definition Language. You use it to describe the data schema,
tables and relations between them. We saw it in action in the
[initDb function][0623] back in the sample project.

#### Create table, alter table, drop table

We talk SQL, we talk [tables][0640].

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

The concept of [identity][0626], from psychology, helps to solve it. You can
read the [theseus ship paradox][0627] to get things even more clear: data is
subject to change over the time, yet each record is supposed to be equal to
itself no matter the changes.

##### 'Natural' vs 'artificial' keys

Another common debate is whether to adopt 'natural' or 'artificial' keys.

By natural understand some trait that already exists along the characteristics
of the [entity][0634] being represented by a table.

A social security number could be considered a natural key.

The `ìd` column is an artificial key. It makes sense in the table context but is
completely alien to a person in the real world.

In most cases, **prefer artificial keys**. Natural ones are subject to change
due real world events and thus outside the RDBMS control.

If for some reason you want to use special names for your table, use double
quotes:

##### Double quotes

```sql
create table "never tell me the odds"
(
    id             integer primary key,
    "odd col name" text not null
);

insert into "never tell me the odds" ("odd col name")
values ('han shoots first');

select *
from "never tell me the odds";
```

Resulting in:

| id | "odd col name    |
|----|------------------|
| 1  | han shoots first |

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

Those two concepts will make more sense in the further topics.

#### Foreign keys

Let's talk about the "Relational" in Relational Database Management System.

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
friend. It is represented in the `friends` table by the use of a
[foreign key][0641] pointing to the `address` primary key.

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

We'll come back to this cascade thing in upcoming topics.

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
values on them. This is how column definitions works, you can define keys,
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

What does it mean? it means, according to our table definition, that i don't
accept friends who doesn't have an email. Uncivilized people.

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
your insert will fail. The [id thing][0626] from psychology, remember?

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

Tables with foreign keys marked with `not null` restriction will perform extra
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
values
returning *
```

The returning part (which MySQL doesn't have but every other database runtime
does, even sqlite) helps to know how the newly created record looks like. Here's
a sample output:

| id | created             |
|----|---------------------|
| 1  | 2024-05-28 13:05:48 |

Now the item insert will pass -- as long as the `orders_id` exists as an `id` in
the order table:

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

But the name is too difficult to use, and [what is that '' thing][0642]?

You can use an alternative update:

```sql
update foods
set is_sweet = true
where description like '%honey%';
```

The [like operator][0635] looks for similarities to a given special string.

But the most safe, straightforward way to update a record is knowing its id:

```sql
update foods
set is_sweet = true
where id = 6;
```

We started to use the [where clause][0636] in our queries, more on that up next.

If we need to get rid of some data, the `delete` can be used:

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
    id          identity not null primary key,
    description text     not null
);

create table member
(
    id      identity not null primary key,
    team_id integer  not null,
    name    text     not null,
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
alter table member
    drop constraint fk_member_team_id;
alter table member
    add constraint fk_member_team_id foreign key (team_id) references team (id) on delete cascade;
```

Now your deletion goes even better than expected:

```console
TODOS.H2.DB.PUBLIC> delete from team where id = 2
[2024-05-28 21:26:35] 1 row affected in 2 ms
```

Check what's left in the database:

```sql
select *
from team
         join member on member.team_id = team.id;
```

| team.id | description | member.id | team_id | name  | 
|---------|-------------|-----------|---------|-------|
| 1       | team 1      | 1         | 1       | Joe   |
| 1       | team 1      | 2         | 1       | Anna  |
| 1       | team 1      | 3         | 1       | Lucas |

#### Select

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
select 'Hi mom',
       123,
       current_timestamp,
       lower('DON''T YELL AT ME!'),
       true;
```

When selecting you can mix things with data from your table:

```sql
select concat('the team member ', name, ' from team ', description) as headline
from team
         join member on member.team_id = team.id;
```

##### Select to insert

It is possible to use the select output as the values to an insert statement. It
is a powerful move when transforming data already present inside the database.

For example, i want to know all possible color combinations from my color
database, first i create a table to save my colors, add some, create a second
table to store my combinations and instead of feed combinations one by one i
can do the insert into / select operation:

```sql
-- drop table if exists colors;
create table colors
(
    id   integer primary key,
    name varchar(255) not null
);

insert into colors (name)
values ('red'),
       ('green'),
       ('blue'),
       ('cyan'),
       ('purple');

-- drop table if exists color_combinations;
create table color_combinations
(
    color1 integer not null,
    color2 integer not null,
    constraint color_combinations_fk_colors_id_1 foreign key (color1) references colors (id) on delete cascade,
    constraint color_combinations_fk_colors_id_2 foreign key (color2) references colors (id) on delete cascade,
    constraint color_combinations_pk primary key (color1, color2)
);

-- feed the combinations
insert into color_combinations
select c1.id, c2.id
from colors c1,
     colors c2
where c1.id <> c2.id;

select *
from color_combinations cc
         join colors c1 on cc.color1 = c1.id
         join colors c2 on cc.color2 = c2.id
```

This results into this remarkable dataset:

| color1 | color2 | id | name   | id | name   |
|:-------|:-------|:---|:-------|:---|:-------|
| 1      | 2      | 1  | red    | 2  | green  |
| 1      | 3      | 1  | red    | 3  | blue   |
| 1      | 4      | 1  | red    | 4  | cyan   |
| 1      | 5      | 1  | red    | 5  | purple |
| 2      | 1      | 2  | green  | 1  | red    |
| 2      | 3      | 2  | green  | 3  | blue   |
| 2      | 4      | 2  | green  | 4  | cyan   |
| 2      | 5      | 2  | green  | 5  | purple |
| 3      | 1      | 3  | blue   | 1  | red    |
| 3      | 2      | 3  | blue   | 2  | green  |
| 3      | 4      | 3  | blue   | 4  | cyan   |
| 3      | 5      | 3  | blue   | 5  | purple |
| 4      | 1      | 4  | cyan   | 1  | red    |
| 4      | 2      | 4  | cyan   | 2  | green  |
| 4      | 3      | 4  | cyan   | 3  | blue   |
| 4      | 5      | 4  | cyan   | 5  | purple |
| 5      | 1      | 5  | purple | 1  | red    |
| 5      | 2      | 5  | purple | 2  | green  |
| 5      | 3      | 5  | purple | 3  | blue   |
| 5      | 4      | 5  | purple | 4  | cyan   |

How to make it a [combination instead a permutation][0643] is left to you as an
exercise.

##### Select to create table

In a similar fashion, you can use a select to create a table:

```sql
create table ccombs as
select c1.name as name1, c2.name as name2
from color_combinations cc
         join colors c1 on cc.color1 = c1.id
         join colors c2 on cc.color2 = c2.id
where c1.id < c2.id

select *
from ccombs;
```

| name1 | name2  |
|-------|--------|
| red   | green  |
| red   | blue   |
| red   | cyan   |
| red   | purple |
| green | blue   |
| green | cyan   |
| green | purple |
| blue  | cyan   |
| blue  | purple |
| cyan  | purple |

Note however that the resulting table is pretty much poorly designed. it does
not have any constraints, no primary key, just raw, synthetic data. Sure, you
can fix some of those issues with `alter table` statements, but the victorious
approach here is to create the table first and then go with insert/select
strategy.

##### Join and union

[Join operations][0644] (which we've been using for a while now) combine the
**records** of two or more tables. Keep that in mind, it involves various tables
but the operation is at record level. Why is that important?

```sql
select c1.*, c2.*
from colors c1,
     colors c2;
```

| id | name   | id | name   |
|:---|:-------|:---|:-------|
| 1  | red    | 1  | red    |
| 1  | red    | 2  | green  |
| 1  | red    | 3  | blue   |
| 1  | red    | 4  | cyan   |
| 1  | red    | 5  | purple |
| 2  | green  | 1  | red    |
| 2  | green  | 2  | green  |
| 2  | green  | 3  | blue   |
| 2  | green  | 4  | cyan   |
| 2  | green  | 5  | purple |
| 3  | blue   | 1  | red    |
| 3  | blue   | 2  | green  |
| 3  | blue   | 3  | blue   |
| 3  | blue   | 4  | cyan   |
| 3  | blue   | 5  | purple |
| 4  | cyan   | 1  | red    |
| 4  | cyan   | 2  | green  |
| 4  | cyan   | 3  | blue   |
| 4  | cyan   | 4  | cyan   |
| 4  | cyan   | 5  | purple |
| 5  | purple | 1  | red    |
| 5  | purple | 2  | green  |
| 5  | purple | 3  | blue   |
| 5  | purple | 4  | cyan   |
| 5  | purple | 5  | purple |

Each record from the `colors` table [aliased][0645] as `c1` was combined with
each record from the same table, but aliased as `c2`. This is called a
[cartesian product][0646] and not always desirable.

As we saw previously, you avoid that by proper define either a where clause or
conditions in the join. Both join forms bellow solves the issue:

```sql
select c1.*, c2.*
from colors c1,
     colors c2
where c1.id < c2.id;

select c1.*, c2.*
from colors c1
         join colors c2 on c1.id < c2.id;
```

Joins has [variations][0647]:

- **left join**, when the condition in **joining** table involves null values.
- **right join**, when the condition in **joined** table involves null values.
- **(inner) join**, when null values **are not allowed** in the junction. On
  most RDMS the inner is optional, and the comma-separated syntax is equivalent
  to it, like the example above.
- **cross outer join**, when null values **are allowed** in the junction.

Now, if you want to append distinct yet slightly similar resuls you need to do a
[union][0648]. For example for this schema and data:

```sql
create table colonels
(
    id   integer primary key,
    name text not null
);
create table captains
(
    id   integer primary key,
    name text not null
);
create table soldiers
(
    id   integer primary key,
    name text not null
);

insert into colonels(name)
values ('Clark'),
       ('Mark'),
       ('Oliver');
insert into captains(name)
values ('Barry'),
       ('Larry');
insert into soldiers(name)
values ('Joe');

-- i can query each table, one by one, of course, but i can union them and even
-- polish results a little so no information is left out:

select '1 - colonel' as rank, name
from colonels
union
select '2 - captain' as rank, name
from captains
union
select '3 - soldier' as rank, name
from soldiers
order by rank;
```

| rank        | name   |
|:------------|:-------|
| 1 - colonel | Clark  |
| 1 - colonel | Mark   |
| 1 - colonel | Oliver |
| 2 - captain | Barry  |
| 2 - captain | Larry  |
| 3 - soldier | Joe    |

Pretty nice, right?

##### CTE - Common Table Expression

Queries can grow more and more complex depending on the kind of information we
must extract. Joins, subqueries, views, things can grow in complexity pretty
fast.

One approach to ease such pain is to identify the duplicity in your subqueries
and promote them to CTE's.

Let's exemplify the issue and then how to ease things with this database schema:

```sql
-- pragma foreign_keys = 1;

-- a table for products. key characteristics, hardly changing, goes here. 
create table products
(
    id          integer primary key,
    description text      not null,
    created     timestamp not null default current_timestamp
);

-- a table for history price. prices will vary over time. 
create table prices_history
(
    id          integer primary key,
    value       decimal(10, 4) not null,
    created     timestamp      not null default current_timestamp,
    products_id integer        not null,
    foreign key (products_id) references products (id),
    unique (products_id, created)
);

-- a table to know the product stock position in a given moment in time.
create table stock_history
(
    id          integer primary key,
    amount      integer   not null default 0,
    created     timestamp not null default current_timestamp,
    products_id integer   not null,
    foreign key (products_id) references products (id),
    unique (products_id, created)
);

-- order table to identify a transaction
create table orders
(
    id          integer primary key,
    -- this should be calculated from all items in this order
    total_price decimal(10, 4) not null,
    created     timestamp      not null default current_timestamp
);

-- order item to know how many products where involved in the transaction, how
-- much they costed and what was the stock position when this transaction
-- happened. 
create table order_items
(
    id                integer primary key,
    amount            integer   not null default 1,
    created           timestamp not null default current_timestamp,
    orders_id         integer   not null,
    products_id       integer   not null,
    prices_history_id integer   not null,
    stock_history_id  integer   not null,
    foreign key (orders_id) references orders (id),
    foreign key (products_id) references products (id),
    foreign key (prices_history_id) references prices_history (id),
    foreign key (stock_history_id) references stock_history (id),
    -- product appears a single time per transaction, 
    -- with a stock and a price properly trackable in time
    unique (orders_id, products_id),
    unique (orders_id, products_id, prices_history_id),
    unique (orders_id, products_id, stock_history_id)
);
```

I want to sell things and keep track on how exactly it affects my product stock
and how much it used to cost by the time i sold it. It's not that complex, but
believe me, sell things is hard.

Let's feed some data:

```sql
-- pragma foreign_keys = 1;
-- some base products
insert into products(id, description)
values (1, 'Apple'),
       (2, 'Banana'),
       (3, 'Milk'),
       (4, 'Toy Airplane');

-- initial prices
insert into prices_history (id, products_id, value)
VALUES (1, 1, 5),
       (2, 2, 3),
       (3, 3, 5.5),
       (4, 4, 25);

-- initial stock
insert into stock_history (id, products_id, amount)
values (1, 1, 100),
       (2, 2, 500),
       (3, 3, 100),
       (4, 4, 30);

-- now create an order
insert into orders(id, total_price)
values (1, 100);

-- and some items to it
insert into order_items(orders_id, products_id, prices_history_id, stock_history_id, amount)
values (1, 1, 1, 1, 10), -- 10 apples at $5 each
       (1, 4, 4, 4, 2);
-- 2 toys at $25 each

-- let's update our stocks
insert into stock_history (id, products_id, amount)
values (5, 1, 90), -- i just sold 10 apples
       (6, 4, 28);
-- just sold 2 airplane toys

-- now let's change some prices just because.
insert into prices_history (id, products_id, value)
VALUES (5, 1, 10),
       (6, 2, 9);

-- and place a new order
insert into orders(id, total_price)
values (2, 100);

insert into order_items(orders_id, products_id, prices_history_id, stock_history_id, amount)
values (2, 1, 5, 5, 20), -- 20 apples at $10 each
       (2, 2, 6, 2, 100);
-- 100 bananas at $9 each

-- now update the stock, keep the stock up to date
insert into stock_history (id, products_id, amount)
values (7, 1, 70),
       (8, 2, 400);

-- oof! boy that reminds me my days as a salesman.
```

Now, let's ask some questions to this database:

1. how many different items i've sold so far?
1. what was the paid amount in the cheapest order item?
1. what is my latest stock position for each product?
1. what is the initial and current price for each product?

The following queries can answer that:

```sql
-- Answer #1
select count(distinct products_id) as different_items 
from order_items;

-- Answer #2
select min(paid_price) as paid_amount
from (select amount * value as paid_price
      from order_items oi
             join prices_history ph on oi.prices_history_id = ph.id);

-- Answer #3
select latest.latest_id, latest.products_id, sh.amount
from (select max(id) as latest_id, products_id
      from stock_history
      group by products_id) as latest
       join stock_history sh on sh.id = latest.latest_id;

-- Answer #4

```

#### Sum, avg, count, group by

#### Order by, limit, offset

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

[0637]: https://h2database.com/html/tutorial.html#tutorial_starting_h2_console

[0638]: https://www.iso.org/standard/76583.html

[0639]: https://en.wikibooks.org/wiki/Structured_Query_Language

[0640]: https://www.sqlshack.com/an-introduction-to-sql-tables/

[0641]: https://www.programiz.com/sql/foreign-key

[0642]: https://learnsql.com/cookbook/how-to-escape-single-quotes-in-sql/

[0643]: https://www.mathsisfun.com/combinatorics/combinations-permutations.html

[0644]: https://learnsql.com/blog/sql-join-examples-with-explanations/

[0645]: https://learnsql.com/blog/sql-join-cheat-sheet/joins-cheat-sheet-a4.pdf

[0646]: https://en.wikipedia.org/wiki/Cartesian_product

[0647]: https://learnsql.com/blog/left-join-examples/

[0648]: https://www.sqltutorial.net/union.html

