package com.example.camerax

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import kotlin.enums.EnumEntries

private val PERMISSIONS = arrayOf(
    android.Manifest.permission.CAMERA
)

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
            CameraXFeaturesScreen(CameraXFeature.entries) { cameraXFeature ->
                controller.navigate(cameraXFeature.name)
            }
        }
        CameraXFeature.entries.forEach { cameraXFeature ->
            composable(route = cameraXFeature.name) {
                cameraXFeature.render()
            }
        }
    }
}

@Composable
fun CameraXFeaturesScreen(
    features: EnumEntries<CameraXFeature>,
    onClick: (CameraXFeature) -> Unit
) {
    val context = LocalContext.current
    var isPermissionGranted by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        isPermissionGranted = it.values.all { it }
    }
    LaunchedEffect(Unit) {
        isPermissionGranted = PERMISSIONS.all { permission ->
            PermissionChecker.checkSelfPermission(
                context,
                permission
            ) == PermissionChecker.PERMISSION_GRANTED
        }
    }
    if (isPermissionGranted) {
        LazyColumn(
            Modifier.fillMaxSize()
        ) {
            items(features.toList()) { cameraXFeature ->
                Card(
                    onClick = { onClick(cameraXFeature) }
                ) {
                    Text(cameraXFeature.name)
                }
            }
        }
        return
    }

    Box(Modifier.fillMaxSize()) {
        Button(
            onClick = { launcher.launch(PERMISSIONS) },
            Modifier.align(Alignment.Center)
        ) {
            Text("We need some permissions")
        }
    }
}

