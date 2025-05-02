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

Finally, `TestContainers` can help by extending the test boundaries to the
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

[0801]: ./0013-unit-tests-part-1.md
