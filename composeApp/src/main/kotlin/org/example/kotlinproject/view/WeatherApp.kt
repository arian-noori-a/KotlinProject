package org.example.kotlinproject.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.kotlinproject.model.ApiClient


@Composable
fun WeatherApp() {
    val navController = rememberNavController()

    Box( modifier = Modifier
        .fillMaxSize()
        .background(ApiClient.getBackGroundColor())
    ) {
        NavHost(navController = navController, startDestination = "MainMenu") {

            composable("MainMenu") { MainMenu(navController) }
            composable("SettingMenu") { SettingScreen(navController) }
            composable("WeatherMenu") { SelectedWeatherMenu(navController) }

        }
    }
}