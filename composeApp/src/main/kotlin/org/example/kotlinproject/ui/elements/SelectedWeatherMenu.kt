package org.example.kotlinproject.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kotlinproject.R.drawable
import org.example.kotlinproject.ui.stateholders.WeatherViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun SelectedWeatherMenu(
    navController: NavController ,
    viewModel: WeatherViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(viewModel.getBackgroundColor())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //purple inside the button color:
        //Color(0XFF6200EE)
        val selectedCity = viewModel.getSelectedCity()
        val textStyle = TextStyle(
            color = viewModel.getTextColor(),
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Text(
            text = selectedCity.name,
            style = textStyle,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 36.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        val timeString = getLocalTime(selectedCity.dt, selectedCity.timezone)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val localTime = LocalTime.parse(timeString, formatter)
        val color: Color = if(localTime.isBefore(LocalTime.of(20 , 0)) and localTime.isAfter(LocalTime.of(6 , 0))) {
            Color(0xFFADD8E6)
        } else {
            Color(0xFF00008B)
        }

        for (weather in selectedCity.weather) {
            val description = weather.description
            val painter = getWeatherImage(description)
            MyImage(painter , color)
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
            val description = "Humidity" + selectedCity.main.humidity.toString() + "%"
            Text(text = description , style = textStyle , modifier = Modifier.fillMaxWidth())
        }
        val settings = viewModel.settings
        var windSpeed = "Wind Speed:"
        when (settings.getInt("Wind" , 0)) {
            0 -> windSpeed += "${selectedCity.wind.speed} m/s"
            1 -> windSpeed += "${"%.2f".format(selectedCity.wind.speed * 3.6)} km/h"
            2 -> windSpeed += "${"%.2f".format(selectedCity.wind.speed * 1.94384)} Knots"
        }
        Text(text = windSpeed , color = viewModel.getTextColor() , fontWeight = FontWeight.ExtraBold)
        var pressure = "Pressure:"
        when (settings.getInt("Pressure" , 0)) {
            0 -> pressure += "${selectedCity.main.pressure} hPa"
            1 -> pressure += "${"%.2f".format(selectedCity.main.pressure * 0.02953)} In Hg"
            2 -> pressure += "${selectedCity.main.pressure / 10} kPa"
            3 -> pressure += "${"%.2f".format(selectedCity.main.pressure * 0.75)} mm Hg"
        }
        Text(pressure , color = viewModel.getTextColor() , fontWeight = FontWeight.ExtraBold)
        var realFeel = "Feels Like:"
        when (settings.getInt("Temperature" , 0)) {
            0 -> realFeel += "${"%.2f".format(selectedCity.main.feels_like)}° C"
            1 -> realFeel += "${"%.2f".format(((9 * selectedCity.main.feels_like) / 5) + 32)}° F"
            2 -> realFeel += "${"%.2f".format(selectedCity.main.feels_like - 273)} K"
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
fun MyImage(painter: Painter , color: Color) {
    Image(
        painter = painter,
        contentDescription = "Weather Image",
        modifier = Modifier
            .size(128.dp)
            .clip(CircleShape)
            .background(color)
            .border(2.dp, Color.Black, CircleShape)
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
