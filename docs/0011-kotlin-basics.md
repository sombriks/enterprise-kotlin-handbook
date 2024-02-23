# Everything possible to do with kotlin with just the compiler and an _idea_

General purpose languages tends to offer everything possible in a very flexible
way so the programmer can enjoy solving the same problem again and again.

There are [exceptions][0150], of course, but that's not the case of kotlin.

Kotlin has [so many features][0151] that one could be easily overwhelmed by it.

## Environment setup

We'll start simple, but don't get fooled, we'll get enterprise code soon.

We'll need kotlin compiler available from command line.

To have it, [first we need to install the JDK][0152], any modern version (17 and
beyond) will serve. Technically speaking, java 11 would be good too, but it's
done and gone for good.

If you don't want to create an oracle account, [there are][0154]
[other JDK providers][0153] and the result will be pretty much the same.

If using linux there is a good chance that your distribution already offers a
[jdk out of the box, from the official repo][0155].

To check if JDK installation went well, call `java` and `javac` commands in the
terminal:

```bash
$ java -version
openjdk version "17.0.9" 2023-10-17
OpenJDK Runtime Environment (Red_Hat-17.0.9.0.9-4) (build 17.0.9+9)
OpenJDK 64-Bit Server VM (Red_Hat-17.0.9.0.9-4) (build 17.0.9+9, mixed mode, sharing)

$ javac -version
javac 17.0.9
```

Now we're good to [install the command line compiler][0156]!

As the official docs state, if using mac, [use brew][0157]; if linux
[use sdkman][0158], if windows, good luck!

The compiler is called `kotlinc` and the expected terminal output follows:

```bash
$ kotlinc -version
info: kotlinc-jvm 1.9.22 (JRE 17.0.9+9)
```

## Running scripts

The compiler enters into interactive mode if no args are passed, and also has a
evaluation mode:

```bash
$ kotlinc -e 'println("hello!")'
hello!
```

### Project: Basic output - Hello world

Kotlin files ends with the .kt extension and in order to be executable they need
a main function:

```bash
echo 'fun main () { println("hello!") }' > HelloWorld.kt
kotlinc HelloWorld.kt

kotlin HelloWorldKt
hello!
```

Since kotlin runs on top of jvm:

```bash
java HelloWorldKt
hello!
```

See [project 001][0159] for further details.

## Project: Files and packages

One important thing to master is how kotlin manages the source code
modularization. It's pretty much what java and python and other languages does.

Given two distinct kotlin files:

```kotlin
// file1.kt
fun hey() = "hey!"
```

```kotlin
// file2.kt
fun main() { println(hey()) }
```

You will need to compile the scripts:

```bash
kotlinc *.kt
```

It will produce two .class files:

```bash
$ ls
file1.kt  File1Kt.class  file2.kt  File2Kt.class  META-INF  README.md
```

As you can see, the kotlin compiler "helped" you and used proper camel case on
the file names.

To run this code:

```bash
$ kotlin File2Kt
hey!
```

Any attempt to run `File1Kt` ends up in error since that class has no main
function to serve as entrypoint:

```bash
$ kotlin File1Kt
error: 'main' method not found in class File1Kt
```

### Packages

The concepts of modules and packages have some interceptions but they have some
key distinctions. Take this example:

```kotlin
package project003

fun main () {
  println("hi from package")
}
```

While modules, in most languages, has a 1:1 equivalence with folders in the
file system, packages does that but tries to stay away from folders because some
systems [might not have this concept][0161].

Anyway, if your script declares a package and you are not in a mainframe, by
compiling it the output will be stored in a folder structure representing the
declared package:

```bash
$ kotlinc file3.kt
$ ls project003
File3Kt.class
$ kotlin project003.File3Kt
hi from package
```

See [project 002][0160] for further details.

## Control flow

### If, else, unless, when, while, for and so on

## Primitive data types: String, Int and so on

## Basic input

### Project: Program arguments

### Project: Environment variables

### Project: Interactive prompt

### Project: Check triangles

## Complex data types: List, Map

## Classes

## Basic I/O: Files

### Project: Simple Agenda

## Concurrency: threads

### Project: File spreading

### Project: Producer and consumer with mutex lock

### Further reading: Coroutines

[0150]: https://go.dev/
[0151]: https://kotlinlang.org/docs/whatsnew1920.html
[0152]: https://www.oracle.com/java/technologies/downloads
[0153]: https://www.azul.com/downloads/?package=jdk#zulu
[0154]: https://aws.amazon.com/corretto/?filtered-posts.sort-by=item.additionalFields.createdDate&filtered-posts.sort-order=desc
[0155]: https://docs.fedoraproject.org/en-US/quick-docs/installing-java
[0156]: https://kotlinlang.org/docs/command-line.html#sdkman
[0157]: https://brew.sh/
[0158]: https://sdkman.io/
[0159]: ../samples/project-001-hello-world/README.md
[0160]: ../samples/project-002-files-and-packages/README.md
[0161]: https://en.wikipedia.org/wiki/Record_Management_Services
