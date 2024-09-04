package org.example.kotlinproject.view


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.example.kotlinproject.db.CityQueries
import org.example.kotlinproject.db.weatherdb
import org.example.kotlinproject.viewmodel.WeatherViewModel


@Composable
fun WeatherApp(context: Context) {

    // to remember the state of the page navigation:
    val navController = rememberNavController()

    // the main object which control the connection between
    // the UI and the data:
    val viewModel: WeatherViewModel = viewModel {
        WeatherViewModel()
    }

    // the database which holds the information of the cities:
    val driver: SqlDriver = AndroidSqliteDriver(
        weatherdb.Schema, context, "weather.db"
    )
    val database = weatherdb(driver)
    val cityQueries: CityQueries = database.cityQueries

    // to clear the database you can use this line:
    //cityQueries.clearDatabase()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(viewModel.getBackgroundColor())
    ) {
        NavHost(navController = navController, startDestination = "MainMenu") {
            composable("MainMenu") {
                MainMenu(navController, viewModel, cityQueries)
            }
            composable("SettingMenu") {
                SettingMenu(navController)
            }
            composable("WeatherMenu") {
                SelectedWeatherMenu(navController , viewModel)
            }
        }
    }
}
