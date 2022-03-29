package com.pandulapeter.wagecounter.presentation.shared

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource

@Composable
fun WageCounterTheme(
    content: @Composable () -> Unit
) = MaterialTheme(
    colors = if (isSystemInDarkTheme()) darkColors(
        primary = colorResource(R.color.primary),
        onPrimary = colorResource(R.color.on_primary),
        secondary = colorResource(R.color.secondary)
    ) else lightColors(
        primary = colorResource(R.color.primary),
        onPrimary = colorResource(R.color.on_primary),
        secondary = colorResource(R.color.secondary)
    ),
    content = content
)