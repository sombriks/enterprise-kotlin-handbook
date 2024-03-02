package project007

import kotlin.concurrent.thread

class ThreadLoops(private val loops: Int, var count: Int = 0) {
  fun start() {
    for (i in 1..loops) {
      thread {
        println("I am thread $i")
        count++
      }
    }
  }
}
