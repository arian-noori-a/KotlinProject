package org.example.kotlinproject.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.example.kotlinproject.model.ApiClient


@Composable
fun SelectedWeatherMenu(navController: NavController) {
    Column (
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text(ApiClient.selectedCity.name)
//        Text(ApiClient.selectedCity.weather.toString())
//        Text(ApiClient.selectedCity.main.humidity.toString())
//        Text(ApiClient.selectedCity.main.pressure.toString())
//        Text(ApiClient.selectedCity.wind.speed.toString())
//        Text(getLocalTime(ApiClient.selectedCity.dt , ApiClient.selectedCity.timezone))
        Button(onClick = {
            navController.navigate("MainMenu")
        }) {
            Text("Back")
        }
    }
}