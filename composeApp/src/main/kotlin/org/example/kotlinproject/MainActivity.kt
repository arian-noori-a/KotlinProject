package org.example.kotlinproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.example.kotlinproject.db.CityQueries
import org.example.kotlinproject.db.weatherdb
import org.example.kotlinproject.view.WeatherApp

class MainActivity : ComponentActivity() {

    private lateinit var database: weatherdb
    private lateinit var cityQueries: CityQueries

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val driver: SqlDriver = AndroidSqliteDriver(weatherdb.Schema, this, "weather.db")
            database = weatherdb(driver)
            cityQueries = database.cityQueries
            WeatherApp(cityQueries)
        }
    }
}
/*
EXAMPLE OF USING SQL

    SELECT:
//            val cities = cityQueries.selectAllCities().executeAsList()
//            Column {
//                cities.forEach { city ->
//                    Text("${city.name}: ${city.temperature}°C, ${city.pressure} hPa, ${city.windSpeed} m/s, ${city.weather}")
//                }
//            }
    INSERT:
//            cityQueries.insertCity(
//                "San Fransisco",    // name
//                18.0,        // temperature
//                1015.0,      // pressure
//                4.0,         // windSpeed
//                "Cloudy"     // weather
//            )
    CLEAR:
//            cityQueries.clearDatabase()
    DELETE:
//            val cityToDelete = "London"
//            cityQueries.selectCityByName(cityToDelete).executeAsOneOrNull()?.let {
//                cityQueries.deleteCityById(it.id)
//                Text("Deleted city: $cityToDelete")
//            }
//            cityQueries.deleteCityByName("tehran")
 */
