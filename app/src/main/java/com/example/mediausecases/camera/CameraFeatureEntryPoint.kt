package com.example.mediausecases.camera

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.PermissionChecker
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mediausecases.Start
import com.example.mediausecases.camera.preview.CameraPreviewFeature
import com.example.mediausecases.compose.EnumMenu
import kotlinx.serialization.Serializable

@Composable
fun CameraFeatureEntryPoint() {
    CameraFeatureRequester {
        CameraFeatureMenu()
    }
}

@Composable
fun CameraFeatureRequester(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val (isCameraGranted, setIsCameraGranted) = remember {
        mutableStateOf(false)
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
        setIsCameraGranted
    )

    LaunchedEffect(Unit) {
        setIsCameraGranted(
            PermissionChecker.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PermissionChecker.PERMISSION_GRANTED
        )
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

@Composable
fun CameraFeatureMenu() {
    val controller = rememberNavController()
    NavHost(
        controller,
        Start
    ) {
        composable<Start> {
            EnumMenu(
                CameraFeature.Preview,
                onClick = controller::navigate
            )
        }
        composable<CameraFeature.Preview> {
            CameraPreviewFeature()
        }
    }
}

sealed interface CameraFeature {
    @Serializable
    data object Preview : CameraFeature
}