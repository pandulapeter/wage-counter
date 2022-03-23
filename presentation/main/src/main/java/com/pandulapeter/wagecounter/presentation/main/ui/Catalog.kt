package com.pandulapeter.wagecounter.presentation.main.ui

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.model.WageStatus
import com.pandulapeter.wagecounter.domain.CalculateWageStatusUseCase
import com.pandulapeter.wagecounter.domain.FormatMonetaryAmountUseCase
import com.pandulapeter.wagecounter.domain.FormatTimeUseCase
import com.pandulapeter.wagecounter.domain.GetConfigurationUseCase
import org.koin.androidx.compose.get

@Composable
fun MainApp(
    getConfiguration: GetConfigurationUseCase = get()
) {
    val timestamp = remember { mutableStateOf(0L) }

    DisposableEffect(Unit) {
        // TODO: Replace with coroutines.
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                timestamp.value = System.currentTimeMillis()
                handler.postDelayed(this, 1000L)
            }

        }
        handler.post(runnable)
        onDispose { handler.removeCallbacks(runnable) }

    }

    MaterialTheme {
        DebugInformation(
            configuration = getConfiguration(),
            timestamp = timestamp.value
        )
    }
}

@Composable
private fun DebugInformation(
    configuration: Configuration?,
    timestamp: Long,
    calculateWageStatus: CalculateWageStatusUseCase = get(),
    formatMonetaryAmount: FormatMonetaryAmountUseCase = get(),
    formatTime: FormatTimeUseCase = get()
) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        text = configuration?.let {
            it.print(
                formatMonetaryAmount = formatMonetaryAmount,
                formatTime = formatTime
            ) + calculateWageStatus(
                currentTimestamp = timestamp,
                configuration = it
            ).print(
                formatTime = formatTime
            )
        } ?: "Missing configuration",
        color = Color.Magenta
    )
}

private fun Configuration.print(
    formatMonetaryAmount: FormatMonetaryAmountUseCase,
    formatTime: FormatTimeUseCase
) =
    "Your schedule is ${formatTime(workDayLengthInMinutes)} hours of work starting at ${"%02d".format(workDayStartHour)}:${"%02d".format(workDayStartMinute)}. The wage is ${
        formatMonetaryAmount(
            currencyFormat = currencyFormat,
            amount = hourlyWage
        )
    } per hour.\n"

private fun WageStatus.print(
    formatTime: FormatTimeUseCase
) = when (this) {
    WageStatus.NotWorking -> "You are outside of your working hours."
    is WageStatus.Working -> "You've been working for ${formatTime(elapsedSecondCount)} minutes and earned $earnedWage."
}

@Preview
@Composable
private fun DebugInformationPreview() {
    DebugInformation(
        configuration = null,
        timestamp = 0L
    )
}