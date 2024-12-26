package com.example.camerax

import android.content.Context
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.core.Feature
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CameraXFeature @Inject constructor(
    @ApplicationContext private val context: Context
) : Feature {
    override fun name() = context.getString(R.string.module_name)

    @Composable
    override fun Body() {
        Text("Hello World")
    }

    @Composable
    override fun MenuIcon() {
        Icon(painterResource(android.R.drawable.ic_menu_camera), name())
    }
}