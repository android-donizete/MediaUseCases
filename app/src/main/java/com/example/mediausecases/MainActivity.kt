package com.example.mediausecases

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mediausecases.camera.CameraFeatureEntryPoint
import com.example.mediausecases.compose.EnumMenu
import com.example.mediausecases.theme.MediaUseCasesTheme
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
                Scaffold { padding ->
                    NavHost(
                        controller,
                        Start,
                        Modifier.padding(padding)
                    ) {
                        composable<Start> {
                            EnumMenu(
                                Feature.Camera,
                                onClick = controller::navigate
                            )
                        }
                        composable<Feature.Camera> {
                            CameraFeatureEntryPoint()
                        }
                    }
                }
            }
        }
    }
}

@Serializable
data object Start

sealed interface Feature {
    @Serializable
    data object Camera : Feature
}