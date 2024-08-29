package org.example.kotlinproject.view

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.example.kotlinproject.model.CityData
import org.example.kotlinproject.model.WeatherResponse
import org.example.kotlinproject.ui.theme.LocalColoring
import org.example.kotlinproject.ui.theme.LocalSpacing
import org.example.kotlinproject.viewmodel.WeatherViewModel
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.text.trimIndent

@Composable
fun WeatherDataView(weather: WeatherResponse , viewModel: WeatherViewModel , navController: NavController) {
    var offsetX by remember { mutableStateOf(0.dp) }
    var dragAmount by remember { mutableFloatStateOf(0f) }

    var temperature = ""
    if(CityData.selectedTemperature == 0)
        temperature = "${"%.2f".format(weather.main.temp)}°C"
    else if(CityData.selectedTemperature == 1)
        temperature = "${"%.2f".format(((9 * weather.main.temp) / 5) + 32)}°F"
    else if(CityData.selectedTemperature == 2)
        temperature = "${"%.2f".format(weather.main.temp - 273)}K"

    var windSpeed = ""
    if(CityData.selectedWindSpeed == 0)
        windSpeed = "${weather.wind.speed} m/s"
    else if(CityData.selectedWindSpeed == 1)
        windSpeed = "${weather.wind.speed * 3.6} km/h"
    else if(CityData.selectedWindSpeed == 2)
        windSpeed = "${weather.wind.speed * 1.94384} Knots"

    var pressure = ""
    if(CityData.selectedPressure == 0)
        pressure = "${weather.main.pressure} hPa"
    else if(CityData.selectedPressure == 1)
        pressure = "${"%.2f".format(weather.main.pressure * 0.02953)} In Hg"
    else if(CityData.selectedPressure == 2)
        pressure = "${weather.main.pressure / 10} kPa"
    else if(CityData.selectedPressure == 3)
        pressure = "${"%.2f".format(weather.main.pressure * 0.75)} mmHg"

    val weatherInfo = """
        City: ${weather.name}
        Temperature: $temperature          
        Weathers
        ${weather.weather.joinToString { "${it.main}: ${it.description}" }}
        Pressure: $pressure
        Humidity: ${weather.main.humidity}%
        WindSpeed: $windSpeed
        Time: ${getLocalTime(weather.dt, weather.timezone)}
    """.trimIndent()

    val dragThreshold = 80.dp

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (offsetX > dragThreshold) {
            Button(
                onClick = {
                    viewModel.removeWeather(weather.name)
                    navController.navigate("MainMenu")
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Text("Remove Weather")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = offsetX)
                .border(width = LocalSpacing.current.extraSmall, color = Color.Gray)
                .padding(LocalSpacing.current.small)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if(offsetX < 80.dp)
                                offsetX = 0.dp
                            if(offsetX > 80.dp)
                                offsetX = 80.dp
                        },
                        onDragCancel = {
                            if(offsetX < 80.dp)
                                offsetX = 0.dp
                            if(offsetX > 80.dp)
                                offsetX = 80.dp
                        }
                    ) { _, dragAmountDelta ->
                        dragAmount += dragAmountDelta
                        offsetX = (offsetX + dragAmountDelta.toDp()).coerceAtLeast(0.dp)
                    }
                }
        ) {
            Text(
                text = weatherInfo,
                color = LocalColoring.current.NightMode.textColor,
                modifier = Modifier.clickable(onClick = {
                    CityData.selectedCity = weather
                    navController.navigate("WeatherMenu")
                })
            )

        }
    }
}

@SuppressLint("NewApi")
fun getLocalTime(dt: Long, timezoneOffset: Int): String {
    val instant = Instant.ofEpochSecond(dt)
    val zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneOffset.ofTotalSeconds(timezoneOffset))
    return zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
}
