package com.pandulapeter.wagecounter.presentation.shared.uiComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pandulapeter.wagecounter.presentation.shared.R

@Composable
fun LabeledValueIndicator(
    label: String,
    value: String
) = Column(
    modifier = Modifier.fillMaxWidth()
) {
    HighlightedCard {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            LabelIndicator(label = label)
            ValueIndicator(time = value)
        }
    }
}

@Composable
private fun LabelIndicator(label: String) = Text(
    text = label,
    fontWeight = FontWeight.Light
)

@Composable
private fun ValueIndicator(time: String) = Text(
    text = time,
    fontSize = 32.sp,
    color = colorResource(R.color.secondary),
    fontWeight = FontWeight.SemiBold
)