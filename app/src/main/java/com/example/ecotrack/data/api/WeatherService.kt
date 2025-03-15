package com.example.ecotrack.data.api

import com.example.ecotrack.data.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather") // Agora o Retrofit vai concatenar corretamente
    fun getWeatherByLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "pt"
    ): Call<WeatherResponse>
}
