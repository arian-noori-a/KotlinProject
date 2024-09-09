package org.example.kotlinproject.data.sources


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.jvm.java

object ApiClient {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Database.getBaseUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    suspend fun getCurrentWeather(
        cityName: String,
    ): WeatherResponse {
        return retrofit.create(ApiService::class.java)
            .getCurrentWeather(cityName, Database.getKey(), "metric")
    }

    private interface ApiService {
        @GET("weather")
        suspend fun getCurrentWeather(
            @Query("q") cityName: String,
            @Query("appid") apiKey: String,
            @Query("units") units: String
        ): WeatherResponse
    }

}
