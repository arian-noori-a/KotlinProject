package org.example.kotlinproject.model

import com.russhwolf.settings.Settings


object CityData {

    // List of the default cities where their weather is on the main menu:
    val cities = mutableListOf(
        "Madrid",
        "Berlin",
        "Paris",
        "New York",
        "London",
        "Tehran"
    )

    lateinit var selectedCity : WeatherResponse


}