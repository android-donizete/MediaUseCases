package com.example.core

import androidx.compose.runtime.Composable

interface Feature {
    fun name(): String

    @Composable
    fun MenuIcon()

    @Composable
    fun Body()
}