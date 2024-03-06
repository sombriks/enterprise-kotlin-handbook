package project012

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class Project012ApplicationTests {

	@Value("\${my.message}")
	private lateinit var msg: String

	@Test
	fun contextLoads() {
	}

	@Test
	fun shouldGreetTestMessage(){
		assertEquals("test greetings", msg)
	}

}
