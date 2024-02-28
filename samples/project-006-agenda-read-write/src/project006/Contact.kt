package project006

class Contact {

  var name = ""
  var number = ""
  var address = ""

  // exercise: solve the empty line case
  constructor(line: String) {
    val parts = line.split(";")
    name = parts[0]
    number = parts[1]
    address = parts[2]
  }

  fun toPretty() = "$name\t$number\t$address"

  fun toSave() = "$name;$number;$address"
}
