package org.example.kotlinproject


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import com.example.kotlinproject.R
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.example.kotlinproject.model.ApiClient
import org.example.kotlinproject.ui.theme.LocalColoring
import org.example.kotlinproject.view.WeatherAppNavGraph


class MainActivity : ComponentActivity() {



    companion object {
        // The key which you get from the data holder website:
        private val API_KEY = ApiClient.getKey()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val settings = createSettings(this)

//            settings.putInt("Mode" , 0)
//            settings.putInt("Wind" , 0)
//            settings.putInt("Temperature" , 0)
//            settings.putInt("Pressure" , 0)





            Box(modifier = Modifier.fillMaxSize()
                .background(LocalColoring.current.NightMode.backgroundColor)) {
                WeatherAppNavGraph(apikey = API_KEY , settings)
            }


        }

    }
    private fun createSettings(context: Context): Settings {
        val sharedPreferences = context.getSharedPreferences("MyAppSettings", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(sharedPreferences)
    }
}