package com.example.camerax

import androidx.compose.runtime.Composable
import com.example.camerax.preview.PreviewScreen

enum class CameraXFeature(
    val render: @Composable () -> Unit
) {
    PREVIEW(render = { PreviewScreen() })
}