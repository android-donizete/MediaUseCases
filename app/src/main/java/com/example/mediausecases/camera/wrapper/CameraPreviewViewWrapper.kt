package com.example.mediausecases.camera.wrapper

import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.setViewTreeLifecycleOwner

@Composable
fun CameraPreviewWrapper(
    modifier: Modifier = Modifier,
    onInit: (PreviewView) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current
    AndroidView(
        modifier = modifier,
        factory = {
            PreviewView(it).apply {
                setViewTreeLifecycleOwner(lifecycle)
                onInit(this)
            }
        }
    )
}