package org.example.kotlinproject.view


import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.kotlinproject.db.CityQueries
import org.example.kotlinproject.model.ApiClient
import org.example.kotlinproject.model.WeatherResponse


class WeatherViewModel : ViewModel() {

    private val apiKey = ApiClient.getKey()
    private val _weatherList = MutableStateFlow<List<WeatherResponse>>(emptyList())
    val weatherList: StateFlow<List<WeatherResponse>> = _weatherList

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    val cities: MutableList<String> = mutableListOf()
    lateinit var cityQueries: CityQueries


    fun fetchWeather(cityName: String , cityQueries: CityQueries) {
        viewModelScope.launch {
            _error.value = null

            try {
                val newWeather = ApiClient.getCurrentWeather(cityName, apiKey)
                val updatedWeatherList = _weatherList.value.filter { it.name != newWeather.name }
                _weatherList.value = listOf(newWeather) + updatedWeatherList
                cityQueries.deleteCityByName(newWeather.name)
                cityQueries.insertCity(
                    newWeather.name,
                    newWeather.main.temp.toDouble(),
                    newWeather.main.pressure.toDouble(),
                    newWeather.wind.speed.toDouble(),
                    newWeather.main.humidity.toDouble(),
                    newWeather.weather.toString())


            } catch (e: Exception) {
                _error.value = "Error in fetching weather: ${e.message}.\n" +
                        "Check your connection and your city name."
            }
        }
    }

    fun removeWeather(cityName: String , cityQueries: CityQueries) {
        _weatherList.value = _weatherList.value.filter { it.name.lowercase() != cityName.lowercase() }
        for (city in cityQueries.selectAllCities().executeAsList()) {
            if(city.name.lowercase() == cityName.lowercase())
                cityQueries.deleteCityByName(city.name)
        }
    }

    fun getBackgroundColor(): Color {
        return if(ApiClient.getSetting().getInt("Mode" , 0) == 0)
            Color.White
        else
            Color.DarkGray
    }

    fun getTextColor(): Color {
        return if(ApiClient.getSetting().getInt("Mode" , 0) == 0)
            Color.Black
        else
            return Color.White
    }

}
