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

    val weatherInfo = """
        City: ${weather.name}
        Temperature: ${"%.2f".format(weather.main.temp)}°C                   
        Weathers
        ${weather.weather.joinToString { "${it.main}: ${it.description}" }}
        Pressure: ${weather.main.pressure} hPa
        Humidity: ${weather.main.humidity}%
        WindSpeed: ${weather.wind.speed} m/s
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
                    contentColor = Color.White // Ensure text is visible against the red background
                ),
                elevation = ButtonDefaults.elevation(0.dp) // Remove default elevation to ensure uniform color
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
                        },
                        onDragCancel = {
                            if(offsetX < 80.dp)
                                offsetX = 0.dp
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
                    CityData.selectedCity = weather.name
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
