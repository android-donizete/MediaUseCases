package com.example.mediausecases

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
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
                        FeaturesEntryPointScreen(features) { feature ->
                            controller.navigate(feature.id() + "navigation")
                        }
                    }
                    features.forEach { feature ->
                        navigation(
                            route = feature.id() + "navigation",
                            startDestination = feature.id(),
                        ) { with(feature) { start() } }
                    }
                }
            }
        }
    }
}

@Composable
fun FeaturesEntryPointScreen(
    features: Set<Feature>,
    onClick: (Feature) -> Unit
) {
    LazyColumn(
        Modifier.fillMaxSize()
    ) {
        items(features.toList()) { feature ->
            Card(
                onClick = { onClick(feature) }
            ) {
                feature.Preview()
            }
        }
    }
}

@Preview
@Composable
private fun FeaturesEntryPointScreenPreview() {
    MediaUseCasesTheme {
        FeaturesEntryPointScreen(
            features = List(10) {
                object : Feature {
                    @Composable
                    override fun Preview() {
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painterResource(android.R.drawable.ic_menu_camera), id())
                            Text(id())
                        }
                    }

                    override fun id() = "Feature $it"

                    override fun NavGraphBuilder.start() {

                    }
                }
            }.toSet()
        ) {

        }
    }
}