package com.example.camerax

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.concurrent.futures.await
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun CameraXFeaturePreview() {
    val viewModel = hiltViewModel<CameraXFeaturePreviewViewModel>()
    CameraXFeaturePreview(
        viewModel.state,
        viewModel::initPreview
    )
}

@Composable
private fun CameraXFeaturePreview(
    state: CameraXFeaturePreviewState,
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

data class CameraXFeaturePreviewState(
    val isLoading: Boolean = true
)

@HiltViewModel
class CameraXFeaturePreviewViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    var state by mutableStateOf(CameraXFeaturePreviewState())
        private set

    fun initPreview(view: PreviewView) {
        val lifecycleOwner = view.findViewTreeLifecycleOwner()
            ?.takeIf { it.lifecycle.currentState != Lifecycle.State.DESTROYED }
            ?: return

        val previewUseCase = Preview
            .Builder()
            .build()
            .apply { surfaceProvider = view.surfaceProvider }

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        viewModelScope.launch {
            val processCameraProvider = ProcessCameraProvider
                .getInstance(context)
                .await()

            processCameraProvider.unbindAll()

            val camera = processCameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                previewUseCase
            )

            state = state.copy(isLoading = false)
        }
    }
}