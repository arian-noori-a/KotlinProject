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

    var selectedCity : String = ""

}