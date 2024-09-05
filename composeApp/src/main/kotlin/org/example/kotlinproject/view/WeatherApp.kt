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
import org.example.kotlinproject.model.Database


@Composable
fun WeatherApp(context: Context , viewModel: WeatherViewModel) {

    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(viewModel.getBackgroundColor())
    ) {
        NavHost(navController = navController, startDestination = "MainMenu") {
            composable("MainMenu") {
                MainMenu(navController, viewModel)
            }
            composable("SettingMenu") {
                SettingMenu(navController , viewModel)
            }
            composable("WeatherMenu") {
                SelectedWeatherMenu(navController , viewModel)
            }
        }
    }
}
