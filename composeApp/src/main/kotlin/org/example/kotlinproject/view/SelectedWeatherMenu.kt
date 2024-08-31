package org.example.kotlinproject.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.example.kotlinproject.model.CityData


@Composable
fun SelectedWeatherMenu(navController: NavController) {
    Column (
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(CityData.selectedCity.name)
        Text(CityData.selectedCity.weather.toString())
        Text(CityData.selectedCity.main.humidity.toString())
        Text(CityData.selectedCity.main.pressure.toString())
        Text(CityData.selectedCity.wind.speed.toString())
        Text(getLocalTime(CityData.selectedCity.dt , CityData.selectedCity.timezone))
        Button(onClick = {
            navController.navigate("MainMenu")
        }) {
            Text("Back")
        }
    }
}