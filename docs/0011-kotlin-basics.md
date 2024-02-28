# Everything possible to do with kotlin with just the compiler and an _idea_

General purpose languages tends to offer everything possible in a very flexible
way so the programmer can enjoy solving the same problem again and again.

There are [exceptions][0150], of course, but that's not the case of kotlin.

Kotlin has [so many features][0151] that one could be easily overwhelmed by it.

## The docs

- The [kotlin manual][0182] should be handy for minor questions on daily work.
- [Kotlin reference][0151] also helps, but keep in mind that sometimes it tell
  you to read the java documentation
- [The playground][0188] helps if you past the phase of use the compiler on
  command line all the time.

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

Things happen from top to bottom, from left to the right unless it's async or
just a function declaration, then it will happen only when it's called. Then
again, top to bottom, left to the right.

But mind the unnamed blocks! The following code is **invalid:**

```kotlin
// flow.kt
fun main() {
    val (first, second) = listOf(1,2,3)
    
    {
     println("Hello, world!!! $second")
    }
}
```

Kotlin thinks the block is the [body of a lambda][0162] then bails out with a
syntax error:

```bash
Expression is treated as a trailing lambda argument; consider separating it from call with semicolon
Unresolved reference: second
```

You do as the output suggests and try to compile again:

```bash
flow.kt:3:8: warning: variable 'first' is never used
  val (first, second) = listOf(1,2,3);
       ^
flow.kt:5:3: warning: the lambda expression is unused. If you mean a block, you can use 'run { ... }'
  {
  ^
```

### Variables

There is a few ways to declare variables:

```kotlin
var x: Int // declaration 
x = 11 // attribution
var y = 12 // shorthand with type inference
```

kotlin is strongly typed, so either you declare with type explicitly or you use
the shorthand version. Just `var x` is illegal

There is also two kinds of constants: `val` and `const val`

```kotlin
const val z = 10

fun main() {
  val x: Int // declaration 
  x = 11 // attribution
  val y = 12 // shorthand with type inference
  // y = 13 // ERROR, illegal
  println("Our values are $x $y $z")
}
```

The difference is `const val` is checked at compile time.

### If, when, while, for and so on

Conditionals are pretty straightforward:

```kotlin
fun isEven(x: Int): Boolean {
  if(x % 2 == 0) {
    return true
  } else {
    return false
  }
}
```

There are nice syntax sugar:

```kotlin
fun isEven(x: Int): Boolean {
  return if(x % 2 == 0) true else false
}
```

The switch has other meaning in kotlin so the keyword for it is `when`:

```kotlin
fun isWorkday(day: String): Boolean {
  return when(day) {
    "Saturday", "Sunday" -> false
    else -> true
  }
}
```

It can yeld values.

While loops are.. wile loops:

```kotlin
var x = 10
while(x > 0) {
  x--
}
```

See [the docs][0163] for complete control flow reference.

## Primitive data types: String, Int and so on

- [Numbers][0164]
- [Booleans][0165]
- [Characters][0166]
- [Strings][0167]
- [Arrays][0168]

## Basic input

Now we know kotlin types and how to communicate using prints to console, let's
ask for user input.

### Project: Program arguments

One way to get user input is by [passing program arguments][0169]:

```kotlin
fun main(args: Array<String>) {
  for(arg in args) println("Received $arg")
}
```

### Project: Environment variables

Another way is to get input is to check [environment variables][0170]
[from the shell][0171]:

```kotlin
fun main() {
  println("current PATH is ${System.getenv("PATH")}")
}
```

Most modern operating systems offers a way to set an environment to the current
running process so the user can tweak some of its behaviors.

### Project: Interactive prompt - Check triangles

Another way to get user input is via interactive command line prompt.

[This project][0172] samples how to do that.

To read from command line kotlin can either use the [readLine function][0173] or
use The [java.util.Scanner][0174] class from jdk.

## Complex data types: List, Map

[List][0175] and [Map][0176] are the main container types for daily usage and
they work as expected: lists are indexed by numbers, starting from zero, maps
are indexed by text keys or _something else_.

Usually we'll create lists with the [listOf][0177] helper function and maps with
the [mapOf][0178] helper function:

```kotlin
val numbers = listOf(1,2,3) // immutable
// val numbers = ArrayList<Int>() // mutable
// numbers.add(1)
// numbers.add(2)
// numbers.add(3)
val languages = mapOf("script" to "javascript", "compiled" to "java") // immutable
// val languages = HashMap<String,String>() // mutable
// languages.put("script", "javascript")
// languages.put("compiled", "java")
```

Please note that kotlin has mutable and immutable versions of lists and maps.

## Classes

Kotlin is versatile and can go from [full functional paradigm][0179] to full
[object-oriented][0180] style. If you know classes from other languages, you can
expect similar stuff in kotlin; classes, interfaces, constructors, attributes,
public and private visibility modifiers and so on.

There are a few special idioms that are worthy to mention:

### Smallest possible class declaration

You can declare a class with no attributes and empty constructor like this:

```kotlin
class Thing
```

This is useful, as we'll see in next chapters, [trust me][0181].

### The most common way to use constructors

They are kinda inlined with class name declaration:

```kotlin
class Thing(val attr: Int) // attr is public and immutable because of val
```

But you can add a second one:

```kotlin
class Thing(val attr: Int) {
    constructor(att: Int, something: String) : this(att) {
        println(something)
    }
}
```

It's possible to make it even more verbose, like java:

```kotlin
class Thing {

    var attr: Int

    constructor(attr: Int) {
      this.attr = attr
    }

    constructor(attr: Int, something: String) {
        this.attr = attr
        println(something)
    }
}
```

See more about classes [here][0182].

## Inheritance and Interfaces

By default, kotlin classes are [final][0190]. You have to explicitly declare
them _open_ so you can extend a class:

```kotlin
open class Base(p: Int)

class Derived(p: Int) : Base(p)
```

To override methods they must be open to it as well:

```kotlin
open class Shape {
    open fun draw() { /*...*/ }
    fun fill() { /*...*/ }
}

class Circle() : Shape() {
    override fun draw() { /*...*/ }
}
```

Kotlin [interfaces][0191] can declare methods that classes can override. Like
java, it's the way to get [polymorphism][0192].

```kotlin
interface MyInterface {
    fun bar()
    fun foo() {
      // optional body
    }
}

class MyClass : MyInterface {
  override fun bar(){}

}
```

You need polymorphism to proper implement the ["I" in SOLID][0193].

## Basic I/O: Files

Input and Output are the very heart of any modern system, side by side with
processing capabilities.

That part in kotlin is pretty much similar to what we see in other languages:

We can read, we can write, the mode can be either character or binary.

Character mode implies an [encoding][0183]. Class names and other utilities get
names like [Reader][0184] or [Writer][0185].

Binary mode deals with bytes and we call them streams. Either
[InputStream][0186] or [OutputStream][0187], depending if data comes in or out
of current process.

The most common source and destination for such streams, readers and writers are
the [files][0194] in the [filesystem][0195], the current and most popular data
storage abstraction.

### Project: Simple Agenda

In [this example][0189] we read a file, transform them in Contacts and perform
some operations. Then we save back to the disk every time a change happens.

## Concurrency: threads

Kotlin has concurrency support built on top of jvm so expect good performance.

### Project: Producer and Consumer

## Concurrency: thread synchronization

### Project: Banking transactions and mutexes

### Further reading: Coroutines

[0150]: https://go.dev/
[0151]: https://kotlinlang.org/api/latest/jvm/stdlib/
[0152]: https://www.oracle.com/java/technologies/downloads
[0153]: https://www.azul.com/downloads/?package=jdk#zulu
[0154]: https://aws.amazon.com/corretto
[0155]: https://docs.fedoraproject.org/en-US/quick-docs/installing-java
[0156]: https://kotlinlang.org/docs/command-line.html#sdkman
[0157]: https://brew.sh/
[0158]: https://sdkman.io/
[0159]: ../samples/project-001-hello-world/README.md
[0160]: ../samples/project-002-files-and-packages/README.md
[0161]: https://en.wikipedia.org/wiki/Record_Management_Services
[0162]: https://kotlinlang.org/docs/lambdas.html#instantiating-a-function-type
[0163]: https://kotlinlang.org/docs/control-flow.html#for-loops
[0164]: https://kotlinlang.org/docs/numbers.html
[0165]: https://kotlinlang.org/docs/booleans.html
[0166]: https://kotlinlang.org/docs/characters.html
[0167]: https://kotlinlang.org/docs/strings.html
[0168]: https://kotlinlang.org/docs/arrays.html
[0169]: ../samples/project-003-guess-number-arg/README.md
[0170]: https://en.wikipedia.org/wiki/Environment_variable
[0171]: ../samples/project-004-guess-number-env/README.md
[0172]: ../samples/project-005-guess-number-interactive/README.md
[0173]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/read-line.html
[0174]: https://docs.oracle.com/javase/tutorial/essential/io/scanning.html
[0175]: https://kotlinlang.org/docs/collections-overview.html#list
[0176]: https://kotlinlang.org/docs/collections-overview.html#map
[0177]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/list-of.html
[0178]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/map-of.html
[0179]: https://www.youtube.com/watch?v=Ed3t4WAe0Co
[0180]: https://www.w3schools.com/kotlin/kotlin_oop.php
[0181]: https://docs.spring.io/spring-framework/reference/core/beans/java/configuration-annotation.html
[0182]: https://kotlinlang.org/docs/classes.html
[0183]: https://en.wikipedia.org/wiki/Character_encoding
[0184]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-reader/
[0185]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-writer/
[0186]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-input-stream/
[0187]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-output-stream/
[0188]: https://play.kotlinlang.org/
[0189]: ../samples/project-006-agenda-read-write/README.md
[0190]: https://kotlinlang.org/docs/inheritance.html
[0191]: https://kotlinlang.org/docs/interfaces.html
[0192]: https://en.wikipedia.org/wiki/Polymorphism_(computer_science)
[0193]: https://en.wikipedia.org/wiki/Interface_segregation_principle
[0194]: https://en.wikipedia.org/wiki/Computer_file
[0195]: https://en.wikipedia.org/wiki/File_system
