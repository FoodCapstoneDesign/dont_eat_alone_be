package io.junseok.mealmate

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class MealmateApplication {
    fun main(args: Array<String>) {
        SpringApplication.run(MealmateApplication::class.java, *args)
    }
}
