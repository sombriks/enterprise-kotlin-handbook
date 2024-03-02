package project007

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ExampleTest {

  @Test
  fun `it should run the loop on threads`() {
    val l1 = ThreadLoops(loops = 100)
    l1.start()
    // exercise: sync threads so the assertion can be `assertEquals`
    Assertions.assertNotEquals(100, l1.count) // concurrency baby
  }

  @Test
  fun `it should run the loop on coroutines`() {
    val l1 = CoroutineLoops(loops = 100)
    l1.start()
    Assertions.assertEquals(100, l1.count) // concurrency but runBlocking
  }
}
