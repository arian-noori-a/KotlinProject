package org.example.kotlinproject


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.kotlinproject.model.Database
import org.example.kotlinproject.view.WeatherApp
import org.example.kotlinproject.view.WeatherViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: WeatherViewModel = viewModel {
                WeatherViewModel()
            }

            Database.run(this , viewModel)
            WeatherApp(this , viewModel)

        }
    }

}

