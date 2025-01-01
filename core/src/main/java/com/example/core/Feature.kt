package com.example.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder

interface Feature {
    @Composable
    fun Preview()

    fun id(): String

    fun NavGraphBuilder.start()
}