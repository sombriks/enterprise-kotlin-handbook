package project015

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration::class)
class ProductControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun shouldListProducts() {
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/products")
        ).andReturn().response

        Assertions.assertEquals(200, result.status)
    }

    @Test
    fun shouldSaveProduct() {
        val product = Product(
            description = "Test product",
            id = null
        )

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product))
        ).andReturn().response

        Assertions.assertEquals(200, result.status)
        Assertions.assertTrue(result.contentAsString.contains("Test product"))
    }
}
