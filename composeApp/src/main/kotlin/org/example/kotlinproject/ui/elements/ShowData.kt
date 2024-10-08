package org.example.kotlinproject.ui.elements

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
import org.example.kotlinproject.data.sources.WeatherResponse
import org.example.kotlinproject.ui.stateholders.WeatherViewModel
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.text.trimIndent


@Composable
fun ShowData(
    weather: WeatherResponse,
    viewModel: WeatherViewModel,
    navController: NavController
) {
    var offsetX by remember { mutableStateOf(0.dp) }
    var dragAmount by remember { mutableFloatStateOf(0f) }
    val settings = viewModel.settings
    var temperature = ""
    when (settings.getInt("Temperature" , 0)) {
        0 -> temperature = "${"%.2f".format(weather.main.temp)}° C"
        1 -> temperature = "${"%.2f".format(((9 * weather.main.temp) / 5) + 32)}° F"
        2 -> temperature = "${"%.2f".format(weather.main.temp - 273)} K"
    }

    var windSpeed = ""
    when (settings.getInt("Wind" , 0)) {
        0 -> windSpeed = "${weather.wind.speed} m/s"
        1 -> windSpeed = "${"%.2f".format(weather.wind.speed * 3.6)} km/h"
        2 -> windSpeed = "${"%.2f".format(weather.wind.speed * 1.94384)} Knots"
    }

    var pressure = ""
    when (settings.getInt("Pressure" , 0)) {
        0 -> pressure = "${weather.main.pressure} hPa"
        1 -> pressure = "${"%.2f".format(weather.main.pressure * 0.02953)} In Hg"
        2 -> pressure = "${weather.main.pressure / 10} kPa"
        3 -> pressure = "${"%.2f".format(weather.main.pressure * 0.75)} mm Hg"
    }

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
                .border(width = 4.dp, color = Color.Gray)
                .padding(8.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (offsetX < 80.dp)
                                offsetX = 0.dp
                            if (offsetX > 80.dp)
                                offsetX = 81.dp
                        },
                        onDragCancel = {
                            if (offsetX < 80.dp)
                                offsetX = 0.dp
                            if (offsetX > 80.dp)
                                offsetX = 81.dp
                        }
                    ) { _ , dragAmountDelta ->
                        dragAmount += dragAmountDelta
                        offsetX = (offsetX + dragAmountDelta.toDp()).coerceAtLeast(0.dp)
                    }
                }
        ) {
            Text(
                text = weatherInfo,
                color = if(settings.getInt("Mode" , 0) == 0) Color.Black else Color.White ,
                modifier = Modifier.clickable(onClick = {
                    viewModel.selectCity(weather)
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
fun isTimeAfterFive(dt: Long, timezoneOffset: Int): Boolean {
    val timeString = getLocalTime(dt, timezoneOffset)
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    // Parse the string to a LocalTime object
    val localTime = LocalTime.parse(timeString, formatter)

    // Create a LocalTime object for 17:00
    val targetTime = LocalTime.of(17, 0)

    // Compare if localTime is after 17:00
    return localTime.isAfter(targetTime)
}