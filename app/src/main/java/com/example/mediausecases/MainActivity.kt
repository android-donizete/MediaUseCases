package com.example.mediausecases

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mediausecases.ui.theme.MediaUseCasesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediaUseCasesTheme {
                val controller = rememberNavController()
                NavHost(controller, Home("my dear user")) {
                    composable<Home> {
                        HomeScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val home = viewModel.home
    HomeScreenInternal(home)
}

@Composable
fun HomeScreenInternal(home: Home) {
    Scaffold { paddings ->
        Text("Hello, ${home.username}", Modifier.padding(paddings))
    }
}

@Serializable
data class Home(
    val username: String
)