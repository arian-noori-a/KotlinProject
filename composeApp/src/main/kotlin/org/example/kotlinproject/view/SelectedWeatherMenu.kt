import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.example.kotlinproject.model.ApiClient
//import org.example.kotlinproject.R // Ensure your R file is properly imported

@Composable
fun SelectedWeatherMenu(navController: NavController) {
    /*Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(ApiClient.getBackGroundColor()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title Text
        Text(
            text = ApiClient.selectedCity.name,
            style = TextStyle(
                color = Color(0xFF6200EE), // Purple color matching default button color
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 16.dp)
        )


        // Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                navController.navigate("MainMenu")
            }
        ) {
            Text("Back")
        }
    }*/
}
