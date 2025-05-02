package project015

import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when` as _when
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.bean.override.mockito.MockitoBean

@SpringBootTest
@Import(TestcontainersConfiguration::class)
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
