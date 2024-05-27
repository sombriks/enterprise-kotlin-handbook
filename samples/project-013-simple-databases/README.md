# Simple database example

How to make code query the database for you.

[Databases are not spreadsheets][dans] and the best way to interact with them is
writing an application.

This brutally simple example shows how to do that using the kotlin language.
This is not a production, enterprise example, but it's needed to better absorb
following samples.

## How to Build

Fist, make sure you have the needed database drivers. They are supposed to be in
the **lib** folder.

```bash
kotlinc src/**/*.kt -include-runtime -d db-manage.jar
```

## How to run

You must include the jdbc drivers in the runtime classpath. Example with h2:

```bash
java -cp lib/h2-2.2.224.jar:db-manage.jar project013.QueryDbKt h2 list
```

Example with sqlite (it has an extra dependency on [sl4j][sl4j]):

```bash
java -cp \
lib/slf4j-api-2.0.9.jar:\
lib/slf4j-simple-2.0.9.jar:\
lib/sqlite-jdbc-3.45.3.0.jar:\
db-manage.jar project013.QueryDbKt sqlite list
```

## Noteworthy

- In old jvm versions, you had to explicitly load the database driver using
  [Class.forName()][forName], but [modern drivers][jdbc4]` does that
  automatically, they just need to be present in classpath. That's why we have
  classpath parameter for run but not to compile.
- The JDBC API is quite verbose and fine-grained, which is bad if you just want
  to get things done. This is why so many other database access libraries where
  built. We'll check some of them in other projects.
- As an **exercise**, improve the command line used to run the application, if
  possible by unifying it into a single command for both database engines.
- A second **exercise** is to add a third database driver of your choice and
  make it work.

[dans]: https://spreadsheetplanet.com/database-vs-spreadsheet/
[forName]: https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#forName-java.lang.String-
[jdbc4]: https://docs.oracle.com/javase/8/docs/api/java/sql/DriverManager.html
[sl4j]: https://www.slf4j.org/
