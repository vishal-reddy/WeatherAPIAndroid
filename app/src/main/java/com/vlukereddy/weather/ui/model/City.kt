package com.vlukereddy.weather.ui.model

data class City(
    val name: String,
    val temperature: Double,
    val temperatureFeelsLike: Double,
    val humidity: Int,
    val uvIndex: Double,
    val condition: Condition
) {
    data class Condition(
        val description: String,
        val iconURL: String,
    )
}
