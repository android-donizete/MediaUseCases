package com.example.mediausecases

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.Feature
import com.example.core.theme.MediaUseCasesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var features: Set<@JvmSuppressWildcards Feature>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediaUseCasesTheme {
                val controller = rememberNavController()
                NavHost(controller, "main") {
                    composable(route = "main") {
                        FeaturesScreen(features) { feature ->
                            controller.navigate(feature.name())
                        }
                    }
                    features.forEach { feature ->
                        composable(route = feature.name()) {
                            feature.Body()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FeaturesScreen(
    features: Set<Feature>,
    onClick: (Feature) -> Unit
) {
    LazyColumn {
        items(features.toList()) { feature ->
            Card(onClick = { onClick(feature) }) {
                Row {
                    feature.MenuIcon()
                    Text(feature.name())
                }
            }
        }
    }
}