package project007

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CoroutineLoops(private val loops: Int, var count: Int = 0) {
  fun start() {
    runBlocking {
      for (i in 1..100) {
        launch {
          println("I am coroutine $i")
          count++
        }
      }
    }
  }
}
