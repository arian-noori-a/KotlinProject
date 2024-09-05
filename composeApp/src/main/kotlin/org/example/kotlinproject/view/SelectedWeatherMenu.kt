package org.example.kotlinproject.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.example.kotlinproject.model.ApiClient
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
        //button color:
        //Color(0XFF6200EE)
        val textStyle = TextStyle(
            color = viewModel.getTextColor(),
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Text(
            text = ApiClient.selectedCity.name,
            style = textStyle,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 36.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        for (weather in ApiClient.selectedCity.weather) {
            val description = weather.description
            val painter = getWeatherImage(description)
            MyImage(painter)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = description,
                style = textStyle,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = Modifier.background(viewModel.getBackgroundColor()).padding(5.dp)) {
            val description = "Humidity" + ApiClient.selectedCity.main.humidity.toString() + "%"
            Text(text = description , style = textStyle , modifier = Modifier.fillMaxWidth())
        }
        val settings = ApiClient.getSetting()
        val weather = ApiClient.selectedCity
        var windSpeed = "Wind Speed:"
        when (settings.getInt("Wind" , 0)) {
            0 -> windSpeed += "${weather.wind.speed} m/s"
            1 -> windSpeed += "${"%.2f".format(weather.wind.speed * 3.6)} km/h"
            2 -> windSpeed += "${"%.2f".format(weather.wind.speed * 1.94384)} Knots"
        }
        Text(text = windSpeed , color = viewModel.getTextColor() , fontWeight = FontWeight.ExtraBold)
        var pressure = "Pressure:"
        when (settings.getInt("Pressure" , 0)) {
            0 -> pressure += "${weather.main.pressure} hPa"
            1 -> pressure += "${"%.2f".format(weather.main.pressure * 0.02953)} In Hg"
            2 -> pressure += "${weather.main.pressure / 10} kPa"
            3 -> pressure += "${"%.2f".format(weather.main.pressure * 0.75)} mm Hg"
        }
        Text(pressure , color = viewModel.getTextColor() , fontWeight = FontWeight.ExtraBold)
        var realFeel = "Feels Like:"
        when (settings.getInt("Temperature" , 0)) {
            0 -> realFeel += "${"%.2f".format(weather.main.feels_like)}° C"
            1 -> realFeel += "${"%.2f".format(((9 * weather.main.feels_like) / 5) + 32)}° F"
            2 -> realFeel += "${"%.2f".format(weather.main.feels_like - 273)} K"
        }
        Text(realFeel , color = viewModel.getTextColor() , fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(10.dp))
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
        contentDescription = "Weather Image",
        modifier = Modifier.size(128.dp)
    )
}


@Composable
fun getWeatherImage(description: String): Painter {
    if(description.contains("clouds"))
        return painterResource(id = drawable.broken_clouds)
    if(description.contains("thunderstorm"))
        return painterResource(id = drawable.thunderstorm)
    if(description.contains("snow"))
        return painterResource(id = drawable.snow)
    if(description.contains("rain") or description.contains("drizzle"))
        return painterResource(id = drawable.shower_rain)
    if(description.contains("clear"))
        return painterResource(id = drawable.clear_sky)
    return painterResource(id = drawable.mist)
}
