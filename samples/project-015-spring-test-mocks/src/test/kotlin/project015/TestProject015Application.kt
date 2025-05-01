package project015

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<Project015Application>().with(TestcontainersConfiguration::class).run(*args)
}
