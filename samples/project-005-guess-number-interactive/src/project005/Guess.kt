package project005

import java.util.Scanner

fun main() {

  val secret = (Math.random() * 10).toInt()

  println("Guess the secret number:")

  Scanner(System.`in`).use { scanner ->
    do {
      val guess = scanner.nextInt()
      if (guess == secret) println("Bingo! the secret number is $guess")
      else println("The secret number is not $guess. try again!")
    } while (guess != secret)
  }
}
