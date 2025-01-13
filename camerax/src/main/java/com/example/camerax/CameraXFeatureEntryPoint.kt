package com.example.camerax

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.PermissionChecker
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.core.FeatureEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CameraXFeatureEntryPoint @Inject constructor(
    @ApplicationContext private val context: Context
) : FeatureEntryPoint {
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

    override fun id() = context.getString(R.string.module_name)

    override fun NavGraphBuilder.start(controller: NavHostController) {
        composable(route = id()) {
            CameraXFeaturesScreen {
                LazyColumn() {
                    item {
                        Button(onClick = {
                            controller.navigate("preview")
                        }) {
                            Text("Camera features")
                        }
                    }
                    item {
                       Button(onClick = {
                            controller.navigate("preview")
                       }) {
                           Text("Preview")
                       }
                    }
                }
            }
        }
        composable(route = "preview") {
            CameraXFeaturePreview()
        }
    }
}

@Composable
fun CameraXFeaturesScreen(
    content: @Composable () -> Unit
) {
    var isCameraGranted by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isCameraGranted = it }

    LaunchedEffect(Unit) {
        isCameraGranted = PermissionChecker.checkSelfPermission(
            context,
            android.Manifest.permission.CAMERA
        ) == PermissionChecker.PERMISSION_GRANTED
    }

    if (isCameraGranted) {
        content()
    } else {
        Box(Modifier.fillMaxSize()) {
            Button(
                onClick = { launcher.launch(android.Manifest.permission.CAMERA) },
                Modifier.align(Alignment.Center)
            ) {
                Text("Camera access is required")
            }
        }
    }
}

