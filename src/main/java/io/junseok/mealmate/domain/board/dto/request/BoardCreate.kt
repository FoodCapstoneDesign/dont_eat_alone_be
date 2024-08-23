package io.junseok.mealmate.domain.board.dto.request

data class BoardCreate(val title: String, val content: String, val restaurantId: Long)
