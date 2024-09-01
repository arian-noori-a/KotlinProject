package org.example.kotlinproject.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.russhwolf.settings.Settings
import org.example.kotlinproject.model.ApiClient
import org.example.kotlinproject.ui.theme.LocalSpacing

@Composable
fun TemperatureTabBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
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


@Composable
fun SettingScreen(navController: NavController) {
    val settings = ApiClient.getSetting()
    var selectedTemperature by remember { mutableIntStateOf(settings.getInt("Temperature" , 0)) }
    var selectedWindSpeed by remember { mutableIntStateOf(settings.getInt("Wind" , 0)) }
    var selectedPressure by remember { mutableIntStateOf(settings.getInt("Pressure" , 0)) }
    var selectedMode by remember { mutableIntStateOf(settings.getInt("Mode" , 0)) }
    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = {
                        navController.navigate("MainMenu")
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
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
                .background(Color.DarkGray)
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.padding(LocalSpacing.current.medium)) {
                Text(
                    text = "Settings",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6
                )

                Spacer(modifier = Modifier.height(16.dp))
                ModeBar (
                    selectedTab = selectedMode,
                    onTabSelected = { index ->
                        settings.putInt("Mode" , index)
                        selectedMode = index
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                TemperatureTabBar(
                    selectedTab = selectedTemperature,
                    onTabSelected = { index ->
                        settings.putInt("Temperature" , index)
                        selectedTemperature = index
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
                WindSpeedBar(
                    selectedTab = selectedWindSpeed,
                    onTabSelected = { index ->
                        settings.putInt("Wind" , index)
                        selectedWindSpeed = index
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
                PressureBar(
                    selectedTab = selectedPressure,
                    onTabSelected = { index ->
                        settings.putInt("Pressure" , index)
                        selectedPressure = index
                    }
                )
            }
        }
    }
}

