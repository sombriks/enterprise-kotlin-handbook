package project015

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    fun postgresContainer(): PostgreSQLContainer<*> = PostgreSQLContainer(
        DockerImageName.parse("postgres:16-alpine")
    ).withEnv(
        mapOf(
            "POSTGRES_DB" to "products",
            "POSTGRES_USER" to "enterprise",
            "POSTGRES_PASSWORD" to "enterprise"
        )
    )
}
