package org.example.kotlinproject.view




import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.russhwolf.settings.Settings


// The main composable function in which we save our menus:
@Composable
fun WeatherAppNavGraph() {
    // to save our navigation state:
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "MainMenu") {

        composable("MainMenu") { WeatherScreen(navController) }
        composable("SettingMenu") { SettingScreen(navController) }
        composable("WeatherMenu") { SelectedWeatherMenu(navController) }
    }
}