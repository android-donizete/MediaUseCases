package com.example.camerax.preview

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.concurrent.futures.await
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    var state by mutableStateOf(PreviewState())
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

            val camera = processCameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                previewUseCase
            )

            state = state.copy(isLoading = false)
        }
    }
}