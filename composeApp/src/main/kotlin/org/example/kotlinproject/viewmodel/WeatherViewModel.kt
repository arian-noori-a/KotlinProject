package org.example.kotlinproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.kotlinproject.model.ApiClient
import org.example.kotlinproject.model.CityData
import org.example.kotlinproject.model.WeatherResponse

class WeatherViewModel(private val apiKey: String) : ViewModel() {

    private val _weatherList = MutableStateFlow<List<WeatherResponse>>(emptyList())
    val weatherList: StateFlow<List<WeatherResponse>> = _weatherList

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchWeather(cityName: String) {
        viewModelScope.launch {

            _error.value = null

//            try {
//
//                val newWeather = ApiClient.getCurrentWeather(cityName, apiKey)
//                val updatedWeatherList = _weatherList.value.filter { it.name != newWeather.name }
//                _weatherList.value = listOf(newWeather) + updatedWeatherList
//
//                if (!CityData.cities.contains(cityName)) {
//                    CityData.cities.add(cityName)
//                }
//
//            } catch (e: Exception) {
//                _error.value = "Error in fetching weather: ${e.message}.\n" +
//                        "Check your connection and your city name."
//            }
        }
    }

    fun removeWeather(cityName: String) {
        CityData.cities.remove(cityName)
    }
}
