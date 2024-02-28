package project006

import java.nio.file.Paths
import kotlin.io.path.forEachLine

class Agenda(private val contacts: MutableList<Contact> = mutableListOf<Contact>()) {

  fun init() {
    // exercise: solve the file not found case
    Paths.get("./contacts.txt").forEachLine(action = { line -> contacts.add(Contact(line)) })
  }

  fun save() {
    Paths.get("./contacts.txt")
        .toFile()
        .writeText(text = contacts.map { it.toSave() }.joinToString("\n"))
  }

  fun list() {
    for (i in 1..contacts.size) println("$i - ${contacts[i-1].toPretty()}")
  }

  fun add() {
    println("provide name:")
    val name = readLine()
    println("provide number:")
    val number = readLine()
    println("provide address:")
    val address = readLine()
    contacts.add(Contact("$name;$number;$address"))
    save()
  }

  fun update() {
    // exercise: validate number against contact list size
    println("which contact you want to modify?")
    val number = readLine()!!.toInt()
    val contact = contacts[number - 1]
    println("provide name:")
    contact.name = readLine()!!
    println("provide number:")
    contact.number = readLine()!!
    println("provide address:")
    contact.address = readLine()!!
    save()
  }

  fun delete() {} // exercise: implement delete

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
