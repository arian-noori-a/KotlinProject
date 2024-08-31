package org.example.kotlinproject.model




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



    var selectedTemperature : Int = 0
    var selectedWindSpeed : Int = 0
    var selectedPressure : Int = 0
    var selectedMode : Int = 0

}