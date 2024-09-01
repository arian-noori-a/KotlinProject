package org.example.kotlinproject.model


import androidx.compose.ui.graphics.Color
import com.russhwolf.settings.Settings
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.jvm.java

object ApiClient {

    private const val KEY = "4e6b57fbb69ef616ce47bd9a4e88686f"
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private val settings: Settings = Settings()
    lateinit var selectedCity : WeatherResponse
    val cities = mutableListOf(
        "Madrid",
        "Berlin",
        "Paris",
        "New York",
        "London",
        "Tehran"
    )


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    suspend fun getCurrentWeather(
        cityName: String,
        apiKey: String
    ): WeatherResponse {
        return retrofit.create(ApiService::class.java)
            .getCurrentWeather(cityName, apiKey, "metric")
    }


    private interface ApiService {
        @GET("weather")
        suspend fun getCurrentWeather(
            @Query("q") cityName: String,
            @Query("appid") apiKey: String,
            @Query("units") units: String
        ): WeatherResponse
    }

    fun getKey(): String {
        return KEY
    }

    fun getSetting(): Settings {
        return settings
    }

    fun getBackGroundColor(): Color {
        return if(settings.getInt("Mode" , 0) == 0)
            Color.White
        else
            Color.DarkGray
    }

    fun getTextColor(): Color {
        return if(settings.getInt("Mode" , 0) == 0)
            Color.Black
        else
            Color.White
    }

}
