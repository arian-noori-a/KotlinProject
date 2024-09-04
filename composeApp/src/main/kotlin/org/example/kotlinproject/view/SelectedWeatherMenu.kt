package org.example.kotlinproject.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.example.kotlinproject.model.ApiClient
import org.example.kotlinproject.viewmodel.WeatherViewModel
import com.example.kotlinproject.R.drawable

@Composable
fun SelectedWeatherMenu(navController: NavController , viewModel: WeatherViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(viewModel.getBackgroundColor())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = ApiClient.selectedCity.name,
            style = TextStyle(
                color = Color(0xFF6200EE),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        val description = ApiClient.selectedCity.weather[0].description
        val painter = getWeatherImage(description)

        MyImage(painter)

        Button(
            onClick = {
                navController.navigate("MainMenu")
            }
        ) {
            Text("Back")
        }

    }
}
@Composable
fun MyImage(painter: Painter) {

    Image(
        painter = painter,
        contentDescription = "My Image",
        modifier = Modifier.size(128.dp)
    )
}


@Composable
fun getWeatherImage(description: String): Painter {
    return when (description.lowercase()) {
        "clear sky" -> painterResource(id = drawable.clear_sky)
        "few clouds" -> painterResource(id = drawable.few_clouds)
        "scattered clouds" -> painterResource(id = drawable.scattered_clouds)
        "broken clouds" -> painterResource(id = drawable.broken_clouds)
        "shower rain" -> painterResource(id = drawable.shower_rain)
        "rain" -> painterResource(id = drawable.rain)
        "thunderstorm" -> painterResource(id = drawable.thunderstorm)
        "snow" -> painterResource(id = drawable.snow)
        "mist" -> painterResource(id = drawable.mist)
        else -> painterResource(id = drawable.clear_sky)
    }
}
