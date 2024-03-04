package project009

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when` as _when

class SampleTest7 {

  lateinit var counter: SimpleCounter

  @BeforeEach
  fun setup() {
    counter = mock()
    _when(counter.actual()).thenReturn(7)
  }

  @Test
  fun `counter should be zeroed`() {
    // do some operations on the mock
    counter.increment()
    counter.decrement()

    // you can assert mocked values
    assertThat(7, equalTo(counter.actual()))

    // and assert method calls too
    verify(counter).increment()
    verify(counter).decrement()
    verify(counter).actual()
  }
}
