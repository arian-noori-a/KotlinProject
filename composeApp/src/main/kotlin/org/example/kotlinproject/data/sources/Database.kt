package org.example.kotlinproject.data.sources

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.russhwolf.settings.Settings
import org.example.kotlinproject.db.CityQueries
import org.example.kotlinproject.db.weatherdb
import org.example.kotlinproject.ui.stateholders.WeatherViewModel

object Database {


    private const val KEY = "4e6b57fbb69ef616ce47bd9a4e88686f"
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private val settings = Settings()
    lateinit var selectedCity : WeatherResponse
    private lateinit var cityQueries: CityQueries
    fun getKey(): String { return KEY }
    fun getBaseUrl() : String { return BASE_URL }
    fun getSettings() : Settings { return settings }
    fun getQueries() : CityQueries { return cityQueries }


    fun run(context: Context , viewModel: WeatherViewModel) {
        val driver: SqlDriver = AndroidSqliteDriver(
            weatherdb.Schema, context, "weather.db"
        )
        val database = weatherdb(driver)
        cityQueries = database.cityQueries

        for (city in cityQueries.selectAllCities().executeAsList()) {
            viewModel.fetchWeather(city.name)
        }

    }

    fun addWeather(weather: WeatherResponse) {
        cityQueries.deleteCityByName(weather.name)
        cityQueries.insertCity(
            weather.name,
            weather.main.temp.toDouble(),
            weather.main.pressure.toDouble(),
            weather.wind.speed.toDouble(),
            weather.main.humidity.toDouble(),
            weather.weather.toString()
            )
    }


}