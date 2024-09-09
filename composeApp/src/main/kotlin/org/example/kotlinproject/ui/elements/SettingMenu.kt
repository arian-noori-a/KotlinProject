package org.example.kotlinproject.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.example.kotlinproject.ui.stateholders.WeatherViewModel


@Composable
fun SettingMenu(navController: NavController , viewModel: WeatherViewModel) {

    val temperatureUnit by viewModel.temperatureUnit.collectAsState()
    val windSpeedUnit by viewModel.windSpeedUnit.collectAsState()
    val pressureUnit by viewModel.pressureUnit.collectAsState()
    val mode by viewModel.mode.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(
                        Icons.Filled.Home,
                        contentDescription = "Home"
                    ) },
                    label = { Text("Home") },
                    selected = false,
                    onClick = {
                        navController.navigate("MainMenu")
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(
                        Icons.Filled.Settings,
                        contentDescription = "Settings"
                    ) },
                    label = { Text(text = "Settings", fontWeight = FontWeight.Bold) },
                    selected = true,
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
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Settings",
                    color = viewModel.getTextColor(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6
                )

                Spacer(modifier = Modifier.height(16.dp))

                ModeBar (
                    selectedTab = mode,
                    onTabSelected = { index ->
                        viewModel.updateMode(index)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                TemperatureBar(
                    selectedTab = temperatureUnit,
                    onTabSelected = { index ->
                        viewModel.updateTemperatureUnit(index)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                WindSpeedBar(
                    selectedTab = windSpeedUnit,
                    onTabSelected = { index ->
                        viewModel.updateWindSpeedUnit(index)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                PressureBar(
                    selectedTab = pressureUnit,
                    onTabSelected = { index ->
                        viewModel.updatePressureUnit(index)
                    }
                )
            }
        }
    }
}


@Composable
fun TemperatureBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("Celsius", "Fahrenheit", "Kelvin")

    TabRow(selectedTabIndex = selectedTab) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(text = title,
                    fontWeight = if(selectedTab == index) FontWeight.ExtraBold else FontWeight.Light) },
                selected = index == selectedTab,
                onClick = { onTabSelected(index) }
            )
        }
    }
}


@Composable
fun WindSpeedBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("m/s", "km/h", "Knots")

    TabRow(selectedTabIndex = selectedTab) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(text = title,
                    fontWeight = if(selectedTab == index) FontWeight.ExtraBold else FontWeight.Light) },
                selected = index == selectedTab,
                onClick = { onTabSelected(index) }
            )
        }
    }
}


@Composable
fun PressureBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("hPa", "In Hg", "kPa" , "mmHg")

    TabRow(selectedTabIndex = selectedTab) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(text = title,
                    fontWeight = if(selectedTab == index) FontWeight.ExtraBold else FontWeight.Light) },
                selected = index == selectedTab,
                onClick = { onTabSelected(index) }
            )
        }
    }
}

@Composable
fun ModeBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("Light Mode", "Night Mode")

    TabRow(selectedTabIndex = selectedTab) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(text = title,
                    fontWeight = if(selectedTab == index) FontWeight.ExtraBold else FontWeight.Light) },
                selected = index == selectedTab,
                onClick = { onTabSelected(index) }
            )
        }
    }
}