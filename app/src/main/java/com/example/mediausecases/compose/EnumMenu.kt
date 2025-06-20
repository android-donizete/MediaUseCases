package com.example.mediausecases.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EnumMenu(
    vararg items: Any,
    onClick: (Any) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        for (item in items) {
            Button(
                onClick = {
                    onClick(item)
                },
                Modifier.fillMaxWidth()
            ) {
                Text(item::class.simpleName.orEmpty())
            }
        }
    }
}