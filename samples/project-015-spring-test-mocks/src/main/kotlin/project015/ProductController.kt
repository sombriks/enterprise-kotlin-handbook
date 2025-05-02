package project015

import org.apache.coyote.BadRequestException
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(private val service: ProductService) {

    @GetMapping
    fun list(): List<Product> = service.list()
    @GetMapping("/{id}")
    fun find(@PathVariable id: Long): Product? = service.find(id)
    @PostMapping
    fun insert(product: Product): Product = service.save(product)
    @PutMapping("/{id}")
    fun uprate(@PathVariable id: Long, product: Product): Product {
        if (id != product.id)
            throw BadRequestException("Product id mismatch")
        return service.save(product)
    }
    @DeleteMapping("/{id}")
    fun del(@PathVariable id: Long) = service.del(id)
}
