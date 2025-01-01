package com.example.camerax

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core.Feature
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CameraXFeature @Inject constructor(
    @ApplicationContext private val context: Context
) : Feature {
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

    override fun NavGraphBuilder.start() {
        composable(route = id()) {
            Text("Hello Camera X")
        }
    }
}