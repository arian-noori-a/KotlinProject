package org.example.kotlinproject.ui.stateholders


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontVariation
import androidx.datastore.preferences.protobuf.Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.kotlinproject.data.sources.ApiClient
import org.example.kotlinproject.data.sources.Database
import org.example.kotlinproject.data.sources.WeatherResponse


class WeatherViewModel : ViewModel() {

    private val _weatherList = MutableStateFlow<List<WeatherResponse>>(emptyList())
    val weatherList: StateFlow<List<WeatherResponse>> = _weatherList


    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    val settings = Settings()

    private val _temperatureUnit = MutableStateFlow(this.settings.getInt("Temperature" , 0))
    val temperatureUnit: StateFlow<Int> = _temperatureUnit
    private val _windSpeedUnit = MutableStateFlow(this.settings.getInt("Wind" , 0))
    val windSpeedUnit: StateFlow<Int> = _windSpeedUnit
    private val _pressureUnit = MutableStateFlow(this.settings.getInt("Pressure" , 0))
    val pressureUnit: StateFlow<Int> = _pressureUnit
    private val _mode = MutableStateFlow(this.settings.getInt("Mode" , 0))
    val mode: StateFlow<Int> = _mode


    fun updateSettings(unit: Int , part: String) {
        settings.putInt(part , unit)
        when(part) {
            "Mode" -> _mode.value = unit
            "Pressure" -> _pressureUnit.value = unit
            "Wind" -> _windSpeedUnit.value = unit
            "Temperature" -> _temperatureUnit.value = unit
        }
    }


    fun fetchWeather(cityName: String) {
        viewModelScope.launch {
            _error.value = null
            try {
                val newWeather = ApiClient.getCurrentWeather(cityName)
                val updatedWeatherList = _weatherList.value.filter { it.name != newWeather.name }
                _weatherList.value = listOf(newWeather) + updatedWeatherList
                Database.addWeather(newWeather)
            } catch (e: Exception) {
                _error.value = "Error in fetching weather for $cityName: ${e.message}.\n" +
                        "Check your connection and your city name."
            }
        }
    }

    fun removeWeather(cityName: String) {
        _weatherList.value = _weatherList.value.filter { it.name.lowercase() != cityName.lowercase() }
        Database.getQueries().deleteCityByName(cityName)
    }

    fun getBackgroundColor(): Color {
        return if(settings.getInt("Mode" , 0) == 0)
            Color.White
        else
            Color.DarkGray
    }

    fun getTextColor(): Color {
        return if(settings.getInt("Mode" , 0) == 0)
            Color.Black
        else
            return Color.White
    }

    fun selectCity(weatherResponse: WeatherResponse) {
        Database.selectedCity = weatherResponse
    }
    fun getSelectedCity(): WeatherResponse {
        return Database.selectedCity
    }
}
