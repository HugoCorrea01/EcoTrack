package com.example.ecotrack.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/" // ✅ Base URL correta

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // A baseUrl deve terminar com "/"
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
