package project009

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled("not done yet")
class SampleTest4 {

  val counter = SimpleCounter()

  @BeforeEach
  fun setup() {
    counter.increment() // let's start our tests from 1
  }

  @Test
  fun `counter should be zeroed`() {
    counter.increment()
    counter.decrement()
    Assertions.assertEquals(1, counter.actual())
  }
}
