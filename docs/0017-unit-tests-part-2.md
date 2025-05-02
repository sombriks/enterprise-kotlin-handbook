# Unit Tests, Mock scenarios and Integration tests

A key aspect of enterprise code is its testability and spring offers great
support for this.

All goodies seen in [previous chapter about testing][0801] are also available,
along with cool new ones.

## Spring tests basics

Whenever testing spring applications, the IoC container should be available for
a more realistic test scenario. It can be achieved by simply adding the 
`@SpringBootTest` annotation in the test class:

```kotlin
@SpringBootTest
class Project015ApplicationTests {

	@Test
	fun contextLoads() {
	}

}
```

That way, all test cases will have a complete spring context at disposal to its
scenarios.

## MockBean

## MockMvc

## TestContainers

[0801]: ./0013-unit-tests-part-1.md
