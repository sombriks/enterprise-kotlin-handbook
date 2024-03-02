package project007

fun main(args: Array<String>) {
  var loops = 1000
  if (args.size > 0) {
    loops = args.first().toInt()
  }
  println("sampling threads versus coroutines")
  val c1 = ThreadLoops(loops)
  var c2 = CoroutineLoops(loops)

  c1.start()
  c2.start()
}
