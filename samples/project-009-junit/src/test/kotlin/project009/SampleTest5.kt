package project009

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SampleTest5 {

  val counter = SimpleCounter()

  @BeforeEach
  fun setup() {
    counter.increment() // let's start our tests from 1
  }

  @Test
  fun `counter should be zeroed`() {
    counter.increment()
    counter.decrement()
    MatcherAssert.assertThat(1, Matchers.equalTo(counter.actual()))
  }
}
