package org.example.kotlinproject.data.repositories

import com.russhwolf.settings.Settings
import org.example.kotlinproject.data.sources.ApiClient
import org.example.kotlinproject.data.sources.Database
import org.example.kotlinproject.data.sources.WeatherResponse

object WeatherRepository {
    val settings = Database.getSettings()
    val selectedCity = Database.selectedCity

    suspend fun fetchWeather(cityName: String): WeatherResponse {
        return ApiClient.getCurrentWeather(cityName)
    }

    fun addWeather(weather: WeatherResponse) {
        Database.addWeather(weather)
    }

    fun deleteWeather(cityName: String) {
        val cityQueries = Database.getQueries()
        cityQueries.deleteCityByName(cityName)
    }

    fun setSelectedCity(city: WeatherResponse) {
        Database.selectedCity = city
    }
}