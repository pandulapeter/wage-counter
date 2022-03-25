package com.pandulapeter.wagecounter.presentation.summary

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pandulapeter.wagecounter.data.model.WorkStatus
import com.pandulapeter.wagecounter.domain.CalculateWorkStatusUseCase
import com.pandulapeter.wagecounter.domain.FormatHoursMinutesAndSecondsUseCase
import com.pandulapeter.wagecounter.domain.FormatMonetaryAmountUseCase
import com.pandulapeter.wagecounter.domain.FormatWorkHoursUseCase
import com.pandulapeter.wagecounter.domain.GetConfigurationUseCase
import com.pandulapeter.wagecounter.presentation.debugMenu.DebugMenu
import org.koin.androidx.compose.get

@Composable
fun Summary(
    currentTimestamp: Long,
    getConfiguration: GetConfigurationUseCase = get(),
    calculateWorkStatus: CalculateWorkStatusUseCase = get(),
    formatMonetaryAmount: FormatMonetaryAmountUseCase = get(),
    formatWorkHours: FormatWorkHoursUseCase = get(),
    formatHoursMinutesAndSeconds: FormatHoursMinutesAndSecondsUseCase = get(),
    debugMenu: DebugMenu = get()
) = Surface(
    modifier = Modifier.padding(bottom = 8.dp)
) {
    val configuration = getConfiguration()
    Text(
        modifier = Modifier.padding(16.dp),
        text = "Your schedule is ${
            formatWorkHours(
                workDayLengthInMinutes = configuration.dayLengthInMinutes,
                workDayStartHour = configuration.startHour,
                workDayStartMinute = configuration.startMinute
            )
        } with an hourly wage of ${
            formatMonetaryAmount(
                currencyFormat = configuration.currencyFormat,
                amount = configuration.hourlyWage
            )
        }.\n\n" + calculateWorkStatus(
            currentTimestamp = currentTimestamp,
            configuration = configuration
        ).also { wageStatus ->
            debugMenu.updateData(
                currentTimestamp = currentTimestamp,
                configuration = configuration,
                workStatus = wageStatus
            )
        }.print(
            formatHoursMinutesAndSeconds = formatHoursMinutesAndSeconds
        )
    )
}

private fun WorkStatus.print(
    formatHoursMinutesAndSeconds: FormatHoursMinutesAndSecondsUseCase
) = when (this) {
    WorkStatus.NotWorking -> "You are outside of your working hours."
    is WorkStatus.Working -> " - Working for: ${formatHoursMinutesAndSeconds(elapsedSecondCount)}\n" +
            " - Remaining: ${formatHoursMinutesAndSeconds(remainingSecondCount)}\n" +
            " - Earned: $earned"
}