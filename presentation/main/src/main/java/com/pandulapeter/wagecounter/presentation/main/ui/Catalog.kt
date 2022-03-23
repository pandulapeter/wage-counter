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
import com.pandulapeter.wagecounter.domain.FormatMonetaryAmountUseCase
import com.pandulapeter.wagecounter.domain.GetConfigurationUseCase
import org.koin.androidx.compose.get

@Composable
fun MainApp(
    getConfiguration: GetConfigurationUseCase = get()
) {
    MaterialTheme {
        Configuration(getConfiguration())
    }
}

@Composable
private fun Configuration(
    configuration: Configuration?,
    formatMonetaryAmount: FormatMonetaryAmountUseCase = get()
) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        text = configuration?.let {
            "${it.workDayLengthInMinutes} minutes of work starting at ${"%02d".format(it.workDayStartHour)}:${"%02d".format(it.workDayStartMinute)}.\n" +
                    "The wage is ${formatMonetaryAmount(currencyFormat = it.currencyFormat, amount = it.hourlyWage)} per hour."
        } ?: "No configuration",
        color = Color.Cyan
    )
}

@Preview
@Composable
private fun ConfigurationPreview() {
    Configuration(
        configuration = null,
    )
}