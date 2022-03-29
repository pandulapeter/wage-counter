package com.pandulapeter.wagecounter.presentation.shared.uiComponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.pandulapeter.wagecounter.presentation.shared.R

@Composable
fun HighlightedCard(
    content: @Composable () -> Unit
) = Card(
    modifier = Modifier
        .padding(vertical = 8.dp)
        .fillMaxWidth(),
    backgroundColor = colorResource(R.color.primary),
    content = content
)