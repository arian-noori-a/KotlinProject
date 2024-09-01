package org.example.kotlinproject.ui.theme


import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color


data class Theme(
    val nightMode: Colors =  Colors(Color.White , Color.DarkGray),
    val lightMode: Colors = Colors(Color.Black , Color.White),
    val colorful: Colors = Colors(Color.Yellow , Color.Red)
)

val LocalColoring = compositionLocalOf {
    Theme()
}

class Colors(
    val textColor: Color,
    val backgroundColor: Color
)
