# Introduction to JUnit and friends

Like we saw in the previous [chapter][0400], several tools available for kotlin
development where inherited from java ecosystem. It's not different for testing
tools.

The notable one is JUnit.

We're not discussing the merit of write or don't write tests here, i am just
assuming that the need of testable, previsible code is already stablished.

## Project setup

Either have a [maven][0401] or [gradle][0402] project and add the [junit][0403]
to the project config file.

For maven:

```xml
<!-- this is inside the dependencies section in pom.xml -->
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter</artifactId>
  <version>5.10.2</version>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test-junit</artifactId>
  <version>1.9.22</version>
  <scope>test</scope>
</dependency>
```

For gradle:

```groovy
// this is inside the dependencies section in build.gradle.kts
testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.2")
testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.22")
```

Then, following the standard folder layout for maven and gradle projects, you
can write tests inside the `src/test/kotlin` folder.

### Test structure in a nutshell

A test suite is a collection of test cases.

In ~~java~~ kotlin, any class method annotated with [@Test][0404] is considered
a test case.

The [example bellow][0405] tests nothing but pass with success:

```kotlin
package project009

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SampleTest1 {

  @Test
  fun shouldPass() {
    Assertions.assertTrue(true)
  }
}
```

You always need:

- A class (the test suite grouping the test cases)
- A function/method annotated with `@Test`
- At least one assertion. Tests with no assertions passes, but they test nothing
  at all, so provide at least one assertion for your test cases.

Let's do another example. This class:

```kotlin
package project009

class SimpleCounter(private var count: Int = 0) {
  fun increment() = count++
  fun decrement() = count--
  fun actual() = count
}
```

Can be tested like this:

```kotlin
package project009

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SampleTest2 {

  @Test
  fun `counter should be zeroed`() {
    val counter = SimpleCounter()
    counter.increment()
    counter.decrement()
    Assertions.assertEquals(0, counter.actual())
  }
}
```

Still a simple test but we _covered_ functionality. If for some reason the
initial internal state changes, this test will verify if the internal value is
the expected one.

Sometimes we'll need a special initial state, that can be accomplished creating
special methods in the test case and put the `@BeforeEach` annotation:

```kotlin
package project009

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SampleTest3 {

  val counter = SimpleCounter()
    
  @BeforeEach
  fun setup(){
    counter.increment() // let's start our tests from 1
  }

  @Test
  fun `counter should be zeroed`() {
    counter.increment()
    counter.decrement()
    Assertions.assertEquals(1, counter.actual())
  }
}
```

There are situation when you need to skip a test, either something is broken and
out of our hands to fix or it's a work in progress but you _have to_ commit it
without break the [ci/cd pipeline][0406].

For that you can use the [@Disabled][0407] annotation:

```kotlin
package project009

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled("not done yet")
class SampleTest4 {

  val counter = SimpleCounter()
    
  @BeforeEach
  fun setup(){
    counter.increment() // let's start our tests from 1
  }

  @Test
  fun `counter should be zeroed`() {
    counter.increment()
    counter.decrement()
    Assertions.assertEquals(1, counter.actual())
  }
}
```

This is the very basic of JUnit and you can do a lot with it, but it can shine
more with some extra spice.

## Popular plugins/libraries

We're going to sample and discuss a little two popular junit extras. one for
nicer assertions and other for mocks.

### Hamcrest

[Hamcrest][0408] allow us to write better test assertions.

Enable Hamcrest in your project by adding the dependcy coordinates in the
project config file:

Gradle:

```groovy
// dependecy groovy style
testImplementation 'org.hamcrest:hamcrest:2.2'
```

Maven:

```xml
<dependency>
    <groupId>org.hamcrest</groupId>
    <artifactId>hamcrest</artifactId>
    <version>2.2</version>
    <scope>test</scope>
</dependency>
```

Then you can write tests lie this:

```kotlin
package project009

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class SampleTest5 {

  val counter = SimpleCounter()
    
  @BeforeEach
  fun setup(){
    counter.increment() // let's start our tests from 1
  }

  @Test
  fun `counter should be zeroed`() {
    counter.increment()
    counter.decrement()
    MatcherAssert.assertThat(1, Matchers.equalTo(counter.actual()))
  }
}
```

It is very common practice, in java, to [import static][0409] `MatcherAssert.*`
and `Matchers.*`, but there is no such thing as import static in kotlin.

Instead, import function members directly:

```kotlin
package project009

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class SampleTest6 {

  val counter = SimpleCounter()
    
  @BeforeEach
  fun setup(){
    counter.increment() // let's start our tests from 1
  }

  @Test
  fun `counter should be zeroed`() {
    counter.increment()
    counter.decrement()
    assertThat(1, equalTo(counter.actual()))
  }
}
```

### Mockito

Another common library used in tests is [Mockito][0410]. It is useful to create
mocks in the code.

Key things mocks can help us when writing tests:

- Remove dependency on external services (apis, databases, etc)
- Check if components used indirectly are being called
- Easily check exception/alternative workflows

Dependency coordinates:

Maven:

```xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.11.0</version>
    <scope>test</scope>
</dependency>
```

Gradle:

```groovy
testImplementation 'org.mockito:mockito-core:5.11.0'
```

Sample test using mockito:

```kotlin
package project009

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when` as _when

class SampleTest7 {

  lateinit var counter: SimpleCounter

  @BeforeEach
  fun setup() {
    counter = mock()
    _when(counter.actual()).thenReturn(7)
  }

  @Test
  fun `counter should be zeroed`() {
    // do some operations on the mock
    counter.increment()
    counter.decrement()

    // you can assert mocked values
    assertThat(7, equalTo(counter.actual()))

    // and assert method calls too
    verify(counter).increment()
    verify(counter).decrement()
    verify(counter).actual()
  }
}
```

Mockito has a [extensive set of examples][0411] showing off its possibilities.

## Further reading

Check the [sample project][0412] and then head to the [next chapter][0413].

[0400]: ./0012-project-setup-with-maven-and-gradle.md
[0401]: ../samples/project-007-sample-maven/README.md
[0402]: ../samples/project-008-sample-gradle/README.md
[0403]: https://junit.org/junit5/docs/current/user-guide/#overview-getting-started
[0404]: https://junit.org/junit5/docs/current/user-guide/#writing-tests-annotations
[0405]: ../samples/project-009-junit/src/test/kotlin/project009/sample-test-1.kt
[0406]: ./0020-ci-cd-pipelines.md
[0407]: https://junit.org/junit5/docs/current/user-guide/#writing-tests-disabling
[0408]: https://hamcrest.org/JavaHamcrest/tutorial
[0409]: https://docs.oracle.com/javase/8/docs/technotes/guides/language/static-import.html
[0410]: https://site.mockito.org/
[0411]: https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#stubbing_consecutive_calls
[0412]: ../samples/project-009-junit/README.md
[0413]: ./0014-spring.md
