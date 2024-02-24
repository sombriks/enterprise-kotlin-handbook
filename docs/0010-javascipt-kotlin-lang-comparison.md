# Quick Javascript / Kotlin comparison

This part of the guide might be helpful to map noteworthy knowledge from
javascript to kotlin. Detailed kotlin introduction will be proper hendler in the
[next section][0105].

## Online playgrounds

Kotlin has one, brand new.

| Kotlin                      | Javascript             |
|-----------------------------|------------------------|
| [play.kotlinlang.org][0100] | [playcode.io][0101]    |
|                             | [codesandbox.io][0102] |
|                             | [stackblitz.com][0103] |
|                             | [codepen.io][0104]     |
|                             | [npm.runkit.com][0113] |

## Command line interpreters/compilers

Kotlin is general purpose, compiled language. There **is** a compiler and it's
[installable][0106]  in various ways.

For linux, [sdkman][0107] is the best choice, for mac [homebrew][0108].

Javascript can run on command line as well in the form of [node.js][0109], and
there are several ways to install it.

## Online package registries

Kotlin is heavily dependent of [jvm][0110] and [maven registry][0111]. There is
no equivalent tool to npm or yarn, maven and gradle does project management work
but lack a good amount of features. We'll discuss them [later][0112].

## Notable language features and its equivalents

There will be a more detailed section on it in the [next chapter][0105].

### Variables

| Kotlin                         | Javascript   |
|--------------------------------|--------------|
| // regular variables           |              |
| var x: Int                     | var x        |
| var x = 10 // type inference   | let x        |
| // constants                   |              |
| val x: Int = 10                | const x = 10 |
| val x = 10                     |              |
| // compile-time constants      |              |
| const x = 10 // checked        |              |
| const x = something() // error |              |

### Data structures

| Kotlin                            | Javascript                               |
|-----------------------------------|------------------------------------------|
| // lists                          |                                          |
| val x = listOf(1,2,3)             | const x = [1,2,3]                        |
| val y = setOf(1,2,2,3)            | const y = new Set([1,2,3])               |
| // maps                           |                                          |
| val x = mapOf("a" to 1, "b" to 2) | const x = { a: 1, b: 2 }                 |
| // classes                        |                                          |
| class X                           | class X {}                               |
| class Y(val x: String)            | class X { constructor(x) { this.x = x} } |

We'll discuss classes in details [later][0105].

### Destructuring

| Kotlin                                           | Javascript                           |
|--------------------------------------------------|--------------------------------------|
| // lists                                         |                                      |
| val (first, second) = listOf(1,2,3)              | const [first, second] = [1,2,3]      |
| // maps (works with [classes][0116] too)         |                                      |
| val (first, second) = mapOf("a" to 1, "b" to 2)  | const {first, second} = {a: 1, b: 2} |

## Communities to talk about the language

- [slack][0114]
- [reddit][0115]

## Noteworthy extras

[0100]: https://play.kotlinlang.org/#eyJ2ZXJzaW9uIjoiMS45LjIyIiwicGxhdGZvcm0iOiJqYXZhIiwiYXJncyI6IiIsIm5vbmVNYXJrZXJzIjp0cnVlLCJ0aGVtZSI6ImlkZWEiLCJjb2RlIjoiLyoqXG4gKiBZb3UgY2FuIGVkaXQsIHJ1biwgYW5kIHNoYXJlIHRoaXMgY29kZS5cbiAqIHBsYXkua290bGlubGFuZy5vcmdcbiAqL1xuZnVuIG1haW4oKSB7XG4gICAgcHJpbnRsbihcIkhlbGxvLCB3b3JsZCEhIVwiKVxufSJ9
[0101]: https://playcode.io/
[0102]: https://codesandbox.io/
[0103]: https://stackblitz.com/
[0104]: https://codepen.io/
[0105]: 0011-kotlin-basics.md
[0106]: https://kotlinlang.org/docs/command-line.html#sdkman
[0107]: https://sdkman.io/
[0108]: https://brew.sh/
[0109]: https://nodejs.org
[0110]: https://dev.java/learn/getting-started/#setting-up-jdk
[0111]: https://central.sonatype.com/search
[0112]: 0012-project-setup-with-maven-and-gradle.md
[0113]: https://npm.runkit.com/
[0114]: https://surveys.jetbrains.com/s3/kotlin-slack-sign-up
[0115]: https://www.reddit.com/r/Kotlin/
[0116]: https://kotlinlang.org/docs/destructuring-declarations.html#example-returning-two-values-from-a-function
