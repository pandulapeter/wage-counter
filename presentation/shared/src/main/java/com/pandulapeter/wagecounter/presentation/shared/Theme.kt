package com.pandulapeter.wagecounter.presentation.shared

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.pandulapeter.wagecounter.presentation.shared.extensions.resolveThemeAttribute
import org.koin.androidx.compose.get

@Composable
fun WageCounterTheme(
    context: Context = get(),
    content: @Composable () -> Unit
) = MaterialTheme(
    colors = if (isSystemInDarkTheme()) darkColors(
        primary = colorResource(R.color.primary),
        onPrimary = colorResource(R.color.on_primary),
        secondary = colorResource(R.color.secondary),
        background = Color(context.resolveThemeAttribute(com.google.android.material.R.attr.colorSurface))
    ) else lightColors(
        primary = colorResource(R.color.primary),
        onPrimary = colorResource(R.color.on_primary),
        secondary = colorResource(R.color.secondary),
        background = Color(context.resolveThemeAttribute(com.google.android.material.R.attr.colorSurface))
    ),
    shapes = Shapes(
        small = RoundedCornerShape(16.dp),
        medium = RoundedCornerShape(8.dp),
        large = RoundedCornerShape(8.dp)
    ),
    content = content
)