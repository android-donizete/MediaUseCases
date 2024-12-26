package com.example.core

import androidx.compose.runtime.Composable

interface Feature {
    @Composable
    fun Main()

    fun name(): String
}