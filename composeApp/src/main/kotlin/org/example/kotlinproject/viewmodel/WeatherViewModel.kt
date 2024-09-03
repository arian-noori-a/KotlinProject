package org.example.kotlinproject.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.kotlinproject.db.CityQueries
import org.example.kotlinproject.model.ApiClient
import org.example.kotlinproject.model.CityData
import org.example.kotlinproject.model.WeatherResponse

class WeatherViewModel : ViewModel() {

    private val apiKey = ApiClient.getKey()
    private val _weatherList = MutableStateFlow<List<WeatherResponse>>(emptyList())
    val weatherList: StateFlow<List<WeatherResponse>> = _weatherList

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    fun fetchWeather(cityName: String , cityQueries: CityQueries) {
        viewModelScope.launch {

            _error.value = null

            try {

                val newWeather = ApiClient.getCurrentWeather(cityName, apiKey)
                val updatedWeatherList = _weatherList.value.filter { it.name != newWeather.name }
                _weatherList.value = listOf(newWeather) + updatedWeatherList

                if (!CityData.cities.contains(cityName)) {
                    CityData.cities.add(cityName)
                    cityQueries.insertCity(cityName , newWeather.main.temp.toDouble() , newWeather.main.humidity.toDouble() , newWeather.main.pressure.toDouble() , newWeather.weather.toString())
                }
                else {
                    cityQueries.deleteCityByName(cityName)
                    cityQueries.insertCity(cityName , newWeather.main.temp.toDouble() , newWeather.main.humidity.toDouble() , newWeather.main.pressure.toDouble() , newWeather.weather.toString())
                }

            } catch (e: Exception) {
                _error.value = "Error in fetching weather: ${e.message}.\n" +
                        "Check your connection and your city name."
            }
        }
    }

    fun removeWeather(cityName: String , cityQueries: CityQueries) {
        _weatherList.value = _weatherList.value.filter { it.name != cityName }
        _weatherList.value = _weatherList.value.filter { it.name != cityName.lowercase() }
        CityData.cities.remove(cityName)
        CityData.cities.remove(cityName.lowercase())
        cityQueries.deleteCityByName(cityName.lowercase())
        cityQueries.deleteCityByName(cityName)
    }

}
