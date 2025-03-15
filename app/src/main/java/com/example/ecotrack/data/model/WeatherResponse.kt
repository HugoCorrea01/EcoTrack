package com.example.ecotrack.data.model

data class WeatherResponse(
    val main: MainWeather,
    val weather: List<WeatherDescription>
)

data class MainWeather(
    val temp: Float, // Temperatura
    val humidity: Int // Umidade
)

data class WeatherDescription(
    val description: String
)
