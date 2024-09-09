package org.example.kotlinproject.ui.elements


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import org.example.kotlinproject.ui.stateholders.WeatherViewModel


@Composable
fun MainMenu(
    navController: NavController,
    viewModel: WeatherViewModel
) {

    val weatherList by viewModel.weatherList.collectAsState()
    val error by viewModel.error.collectAsState()

    var cityName by remember {
        mutableStateOf("")
    }
    var showError by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(error) {
        if (error != null) {
            showError = true
            delay(3000)
            showError = false
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = "Home"
                        )},
                    label = {
                        Text(
                            text = "Home",
                            fontWeight = FontWeight.Bold
                        )},
                    selected = true,
                    onClick = {
                        navController.navigate("MainMenu")
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = false,
                    onClick = {
                        navController.navigate("SettingMenu")
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(viewModel.getBackgroundColor())
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "Enter the city you want to see its information:",
                    color = viewModel.getTextColor(),
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = cityName,
                    onValueChange = { cityName = it },
                    label = { Text("Enter A City Name") },
                    modifier = Modifier.fillMaxWidth(0.95f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            viewModel.fetchWeather(cityName)
                            cityName = ""
                        }
                    ) {
                        Text("Get Weather")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(weatherList) { weather ->
                        ShowData(weather , viewModel, navController)
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(color = Color.Gray)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            AnimatedVisibility(
                visible = showError,
                enter = fadeIn(), exit = fadeOut(),
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color.Red)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(
                        text = error.toString(),
                        color = viewModel.getTextColor(),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}