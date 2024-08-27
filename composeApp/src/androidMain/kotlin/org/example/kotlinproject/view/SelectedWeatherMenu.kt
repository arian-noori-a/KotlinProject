package org.example.kotlinproject.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.example.kotlinproject.model.CityData


@Composable
fun SelectedWeatherMenu(navController: NavController) {
    Column {
        Text(CityData.selectedCity)
        Button(onClick = {
            navController.navigate("MainMenu")
        }) {
            Text("Back")
        }
    }
}