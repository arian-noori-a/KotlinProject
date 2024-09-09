package org.example.kotlinproject.ui.stateholders


import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.kotlinproject.data.repositories.WeatherRepository
import org.example.kotlinproject.data.sources.Database
import org.example.kotlinproject.data.sources.WeatherResponse


class WeatherViewModel : ViewModel() {

    private val _weatherList = MutableStateFlow<List<WeatherResponse>>(emptyList())
    val weatherList: StateFlow<List<WeatherResponse>> = _weatherList

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    val settings = WeatherRepository.settings

    private val _temperatureUnit = MutableStateFlow(this.settings.getInt("Temperature" , 0))
    val temperatureUnit: StateFlow<Int> = _temperatureUnit
    private val _windSpeedUnit = MutableStateFlow(this.settings.getInt("Wind" , 0))
    val windSpeedUnit: StateFlow<Int> = _windSpeedUnit
    private val _pressureUnit = MutableStateFlow(this.settings.getInt("Pressure" , 0))
    val pressureUnit: StateFlow<Int> = _pressureUnit
    private val _mode = MutableStateFlow(this.settings.getInt("Mode" , 0))
    val mode: StateFlow<Int> = _mode

    fun updateTemperatureUnit(unit: Int) {
        _temperatureUnit.value = unit
        settings.putInt("Temperature", unit)
    }
    fun updateWindSpeedUnit(unit: Int) {
        _windSpeedUnit.value = unit
        settings.putInt("Wind", unit)
    }
    fun updateMode(unit: Int) {
        _mode.value = unit
        settings.putInt("Mode", unit)
    }
    fun updatePressureUnit(unit: Int) {
        _pressureUnit.value = unit
        settings.putInt("Pressure", unit)
    }




    fun fetchWeather(cityName: String) {
        viewModelScope.launch {
            _error.value = null
            try {
                val newWeather = WeatherRepository.fetchWeather(cityName)
                val updatedWeatherList = _weatherList.value.filter { it.name != newWeather.name }
                _weatherList.value = listOf(newWeather) + updatedWeatherList
                WeatherRepository.addWeather(newWeather)
            } catch (e: Exception) {
                _error.value = "Error in fetching weather for $cityName: ${e.message}.\n" +
                        "Check your connection and your city name."
            }
        }
    }

    fun removeWeather(cityName: String) {
        val cityQueries = Database.getQueries()
        _weatherList.value = _weatherList.value.filter { it.name.lowercase() != cityName.lowercase() }
        cityQueries.deleteCityByName(cityName)
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
