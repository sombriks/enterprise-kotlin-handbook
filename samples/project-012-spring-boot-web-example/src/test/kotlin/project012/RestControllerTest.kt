package project012

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@ActiveProfiles("test")
@WebMvcTest(value = [SimpleHello::class])
class RestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGreet() {
        val result = mockMvc.get("/hello").andReturn()
        assertEquals(200, result.response.status)
        assertEquals("test greetings", result.response.getContentAsString())
    }
}
