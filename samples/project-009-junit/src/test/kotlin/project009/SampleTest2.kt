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
