package com.example.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface FeatureEntryPoint {
    @Composable
    fun Preview()

    fun id(): String

    fun NavGraphBuilder.start(controller: NavHostController)
}