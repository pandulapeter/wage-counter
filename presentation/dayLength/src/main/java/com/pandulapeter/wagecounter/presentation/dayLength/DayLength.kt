package com.pandulapeter.wagecounter.presentation.dayLength

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.pandulapeter.wagecounter.data.model.Configuration

@Composable
fun DayLength(
    onConfigurationChanged: (Configuration) -> Unit,
    showSnackbar: (String) -> Unit
) = Button(
    onClick = {
        // TODO: Work in progress. Call onConfigurationChanged()
        showSnackbar("Work in progress")
    }
) {
    Text("Modify day length (TODO)")
}