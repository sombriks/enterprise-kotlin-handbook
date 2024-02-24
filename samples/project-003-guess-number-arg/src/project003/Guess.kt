package project003

fun main(args: Array<String>) {

  val secret = 7

  if (args.size == 0) {
    println("you need to guess a number")
    return
  }
  
  val guess = args.first().toInt()

  if (guess == secret) println(" That's it! you guessed the secret number!")
  else println("whoops, try again!")
}
