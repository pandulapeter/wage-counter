package com.pandulapeter.wagecounter.presentation.main.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pandulapeter.wagecounter.data.model.Configuration

@Composable
fun MainApp(configuration: Configuration?) {
    MaterialTheme {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            text = configuration?.currencyPrefix ?: "No configuration",
            color = Color.Cyan
        )
    }
}

@Preview
@Composable
private fun MainAppPreview() {
    MainApp(null)
}