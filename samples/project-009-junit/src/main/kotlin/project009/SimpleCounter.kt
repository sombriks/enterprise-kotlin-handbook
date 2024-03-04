package project009

class SimpleCounter(private var count: Int = 0) {
  fun increment() = count++
  fun decrement() = count--
  fun actual() = count
}
