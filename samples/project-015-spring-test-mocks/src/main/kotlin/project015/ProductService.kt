package project015

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductService(private val repository: ProductRepository)  {
    fun list(): List<Product> = repository.findAll()
    fun find(id: Long): project015.Product? = repository.findByIdOrNull(id)
    fun save(product: Product): Product = repository.save(product)
    fun del(id: Long) = repository.deleteById(id)

}
