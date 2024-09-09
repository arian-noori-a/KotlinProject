package org.example.kotlinproject.data.repositories

import org.example.kotlinproject.data.sources.ApiClient
import org.example.kotlinproject.data.sources.Database
import org.example.kotlinproject.data.sources.WeatherResponse

object WeatherRepository {
    suspend fun fetchWeather(cityName: String): WeatherResponse {
        return ApiClient.getCurrentWeather(cityName)
    }

//    suspend fun getSavedWeather(): List<WeatherResponse> {
//
//    }

    suspend fun addWeather(weather: WeatherResponse) {
        Database.addWeather(weather)
    }

    suspend fun deleteWeather(cityName: String) {

    }

    val settings = Database.getSettings()

}