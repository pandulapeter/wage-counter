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
import androidx.compose.ui.unit.dp
import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.model.WageStatus
import com.pandulapeter.wagecounter.domain.CalculateWageStatusUseCase
import com.pandulapeter.wagecounter.domain.FormatHoursMinutesAndSecondsUseCase
import com.pandulapeter.wagecounter.domain.FormatMonetaryAmountUseCase
import com.pandulapeter.wagecounter.domain.FormatWorkingHoursUseCase
import com.pandulapeter.wagecounter.domain.GetConfigurationUseCase
import com.pandulapeter.wagecounter.presentation.debugMenu.DebugMenu
import org.koin.androidx.compose.get

@Composable
fun MainApp(
    getConfiguration: GetConfigurationUseCase = get()
) {
    val currentTimestamp = remember { mutableStateOf(0L) }

    DisposableEffect(Unit) {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                currentTimestamp.value = System.currentTimeMillis()
                handler.postDelayed(this, REFRESH_PERIOD)
            }
        }
        handler.post(runnable)
        onDispose { handler.removeCallbacks(runnable) }
    }

    MaterialTheme {
        DebugInformation(
            configuration = getConfiguration(),
            currentTimestamp = currentTimestamp.value
        )
    }
}

private const val REFRESH_PERIOD = 1000L

@Composable
private fun DebugInformation(
    configuration: Configuration,
    currentTimestamp: Long,
    calculateWageStatus: CalculateWageStatusUseCase = get(),
    formatMonetaryAmount: FormatMonetaryAmountUseCase = get(),
    formatWorkingHours: FormatWorkingHoursUseCase = get(),
    formatHoursMinutesAndSeconds: FormatHoursMinutesAndSecondsUseCase = get(),
    debugMenu: DebugMenu = get()
) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        text = "Your schedule is ${
            formatWorkingHours(
                workDayLengthInMinutes = configuration.workDayLengthInMinutes,
                workDayStartHour = configuration.workDayStartHour,
                workDayStartMinute = configuration.workDayStartMinute
            )
        } with a wage of ${
            formatMonetaryAmount(
                currencyFormat = configuration.currencyFormat,
                amount = configuration.hourlyWage
            )
        } per hour.\n\n" + calculateWageStatus(
            currentTimestamp = currentTimestamp,
            configuration = configuration
        ).also { wageStatus ->
            debugMenu.updateData(
                currentTimestamp = currentTimestamp,
                configuration = configuration,
                wageStatus = wageStatus
            )
        }.print(
            formatHoursMinutesAndSeconds = formatHoursMinutesAndSeconds
        ),
        color = Color.Magenta
    )
}

private fun WageStatus.print(
    formatHoursMinutesAndSeconds: FormatHoursMinutesAndSecondsUseCase
) = when (this) {
    WageStatus.NotWorking -> "You are outside of your working hours."
    is WageStatus.Working -> " - Working for: ${formatHoursMinutesAndSeconds(elapsedSecondCount)}\n" +
            " - Remaining: ${formatHoursMinutesAndSeconds(remainingSecondCount)}\n" +
            " - Earned: $earnedWage"
}