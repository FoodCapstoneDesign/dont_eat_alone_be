package io.junseok.mealmate.swagger

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean

@Bean
fun customOpenAPI(): OpenAPI {
    return OpenAPI()
        .info(
            Info()
                .title("Team-MealMate")
                .version("1.0.0")
                .description("MealMate API명세서입니다!")
        )
        .servers(
            listOf(
                Server().url("https://api.meal-mate.shop"),
                Server().url("https://api.meal-mate.shop"),
                Server().url("http://localhost:8080")
            )
        )
}
