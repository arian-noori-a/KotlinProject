package org.example.kotlinproject.view




import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.russhwolf.settings.Settings


// The main composable function in which we save our menus:
@Composable
fun WeatherAppNavGraph(apikey: String , settings: Settings) {
    // to save our navigation state:
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "MainMenu") {

        composable("MainMenu") { WeatherScreen(navController, apikey , settings) }
        composable("SettingMenu") { SettingScreen(navController , settings) }
        composable("WeatherMenu") { SelectedWeatherMenu(navController) }
    }
}