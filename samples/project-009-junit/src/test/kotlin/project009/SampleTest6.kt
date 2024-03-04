package project009

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SampleTest6 {

  val counter = SimpleCounter()

  @BeforeEach
  fun setup() {
    counter.increment() // let's start our tests from 1
  }

  @Test
  fun `counter should be zeroed`() {
    counter.increment()
    counter.decrement()
    assertThat(1, equalTo(counter.actual()))
  }
}
