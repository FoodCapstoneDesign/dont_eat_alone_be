package io.junseok.mealmate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class MealmateApplication
    fun main(args: Array<String>) {
        runApplication<MealmateApplication>(*args)
    }

