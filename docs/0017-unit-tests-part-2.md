# Unit Tests, Mock scenarios and Integration tests

Following our [previous chapter][0805], let's discuss how to properly test more
complex applications.

A key aspect of enterprise code is its testability and spring offers great
support for this. Tests covering great chunks of layers in the application can
be called [integration tests][0806].

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

Mock beans injected by spring are handy, sometimes the test boundaries are
restrained but the IoC container is till needed:

```kotlin

import org.mockito.Mockito.`when` as _when
// ...

@SpringBootTest
class ProductServiceTest {

    @MockitoBean
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var productService: ProductService

    @BeforeEach
    fun setup() {
        _when(productRepository.findAll())
            .thenReturn(listOf(Product(33L, "Shoes")))
    }

    @Test
    fun shouldListProducts() {
        val result = productService.list()
        Assert.assertEquals(33L, result[0].id)
    }
}
```

## MockMvc

When dealing with spring controllers, the ideal way to perform the test is by
simulating the request. `MockMvc` Helps on that matter:

```kotlin
// ...

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldListProducts() {
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/products")
        ).andReturn().response
        
        Assertions.assertEquals(200, result.status)
    }
    
    // ...
}
```

## TestContainers

Finally, [TestContainers][0802] can help by extending the test boundaries to the
database.

If the project uses liquibase or depends on a certain kind of state in the db,
then the container configuration grants this to test scenarios.

The most transparent way to use them is by provisioning a `TestConfiguration`:

```kotlin
//...

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    fun postgresContainer(): PostgreSQLContainer<*> = PostgreSQLContainer(
        DockerImageName.parse("postgres:16-alpine")
    ).withEnv(
        mapOf(
            "POSTGRES_DB" to "products",
            "POSTGRES_USER" to "enterprise",
            "POSTGRES_PASSWORD" to "enterprise"
        )
    )
}
```

Then you import it on testcases needing it:

```kotlin
// ...

@SpringBootTest
@Import(TestcontainersConfiguration::class)
class Project015ApplicationTests {
    @Test
    fun contextLoads() {
    }
}
```

Check the [sample project][0803] to see it in action and then head to the
[next chapter][0804]

[0801]: ./0013-unit-tests-part-1.md
[0802]: https://testcontainers.com
[0803]: ../samples/project-015-spring-test-mocks/README.md
[0804]: ./0018-container-basics.md
[0805]: ./0016-spring-with-databases.md
[0806]: https://en.wikipedia.org/wiki/Integration_testing

