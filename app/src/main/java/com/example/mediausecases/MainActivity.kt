package com.example.mediausecases

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.core.FeatureEntryPoint
import com.example.core.theme.MediaUseCasesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var featureEntryPoints: Set<@JvmSuppressWildcards FeatureEntryPoint>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediaUseCasesTheme {
                val controller = rememberNavController()
                Scaffold { padding ->
                    NavHost(controller, "main", Modifier.padding(padding)) {
                        composable(route = "main") {
                            FeaturesEntryPointScreen(featureEntryPoints) { feature ->
                                controller.navigate(feature.id() + "navigation")
                            }
                        }
                        featureEntryPoints.forEach { feature ->
                            navigation(
                                route = feature.id() + "navigation",
                                startDestination = feature.id(),
                            ) { with(feature) { start(controller) } }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FeaturesEntryPointScreen(
    featureEntryPoints: Set<FeatureEntryPoint>,
    onClick: (FeatureEntryPoint) -> Unit
) {
    LazyColumn(
        Modifier.fillMaxSize()
    ) {
        items(featureEntryPoints.toList()) { feature ->
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
            featureEntryPoints = List(10) {
                object : FeatureEntryPoint {
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

                    override fun NavGraphBuilder.start(controller: NavHostController) {

                    }
                }
            }.toSet()
        ) {

        }
    }
}