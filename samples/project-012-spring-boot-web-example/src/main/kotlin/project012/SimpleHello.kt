package project012

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("hello")
class SimpleHello(@Value("\${my.message}") private val msg: String) {

  @GetMapping fun greet() = msg
}
