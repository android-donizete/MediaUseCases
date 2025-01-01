package com.example.camerax.preview

import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.setViewTreeLifecycleOwner

@Composable
fun PreviewScreen() {
    val viewModel = hiltViewModel<PreviewViewModel>()
    PreviewScreen(
        viewModel.state,
        viewModel::initPreview
    )
}

@Composable
private fun PreviewScreen(
    state: PreviewState,
    initPreview: (PreviewView) -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        val lifecycle = LocalLifecycleOwner.current
        AndroidView(
            factory = {
                PreviewView(it).apply {
                    setViewTreeLifecycleOwner(lifecycle)
                    initPreview(this)
                }
            }
        )
        if (state.isLoading) {
            Spacer(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            )
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}