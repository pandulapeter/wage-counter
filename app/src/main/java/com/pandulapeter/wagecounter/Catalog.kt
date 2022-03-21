package com.pandulapeter.wagecounter

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MainApp() {
    MaterialTheme {
        Text(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            text = "Hello world",
            color = Color.Cyan
        )
    }
}