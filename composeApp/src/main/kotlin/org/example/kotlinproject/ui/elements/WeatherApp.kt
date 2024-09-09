package org.example.kotlinproject.ui.elements


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.kotlinproject.ui.stateholders.WeatherViewModel


@Composable
fun WeatherApp(viewModel: WeatherViewModel) {

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
