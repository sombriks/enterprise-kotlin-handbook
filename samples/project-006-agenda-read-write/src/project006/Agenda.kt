package project006

class Agenda {

  fun init() {}

  fun save() {}

  fun list() {}

  fun add() {}

  fun update() {}

  fun delete() {}

  fun menu() {
    init()
    var option: Int?
    do {
      println("Choose an option:")
      println(
          """
      1 - list contacts
      2 - add new one
      3 - update one
      4 - delete
      5 - exit
      """.trimIndent()
      )
      option = readLine()?.toIntOrNull()
      when (option) {
        1 -> list()
        2 -> add()
        3 -> update()
        4 -> delete()
        5 -> println("bye bye!")
        else -> println("invalid option!")
      }
    } while (option != 5)
  }
}
