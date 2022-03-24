package com.pandulapeter.wagecounter.presentation.main.ui

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.model.WageStatus
import com.pandulapeter.wagecounter.domain.CalculateWageStatusUseCase
import com.pandulapeter.wagecounter.domain.FormatHoursMinutesAndSecondsUseCase
import com.pandulapeter.wagecounter.domain.FormatMonetaryAmountUseCase
import com.pandulapeter.wagecounter.domain.FormatWorkingHoursUseCase
import com.pandulapeter.wagecounter.domain.GetConfigurationUseCase
import com.pandulapeter.wagecounter.domain.UpdateConfigurationUseCase
import com.pandulapeter.wagecounter.presentation.debugMenu.DebugMenu
import com.pandulapeter.wagecounter.presentation.main.R
import org.koin.androidx.compose.get

@Composable
fun MainApp(
    fragmentManager: FragmentManager,
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

    MaterialTheme(
        colors = if (isSystemInDarkTheme()) lightColors(
            primary = colorResource(R.color.primary),
            onPrimary = colorResource(R.color.on_primary)
        ) else darkColors(
            primary = colorResource(R.color.primary),
            onPrimary = colorResource(R.color.on_primary)
        )
    ) {
        DebugInformation(
            fragmentManager = fragmentManager,
            configuration = getConfiguration(),
            currentTimestamp = currentTimestamp.value
        )
    }
}

private const val REFRESH_PERIOD = 1000L

@Composable
private fun DebugInformation(
    fragmentManager: FragmentManager,
    configuration: Configuration,
    currentTimestamp: Long,
    calculateWageStatus: CalculateWageStatusUseCase = get(),
    formatMonetaryAmount: FormatMonetaryAmountUseCase = get(),
    formatWorkingHours: FormatWorkingHoursUseCase = get(),
    formatHoursMinutesAndSeconds: FormatHoursMinutesAndSecondsUseCase = get(),
    updateConfiguration: UpdateConfigurationUseCase = get(),
    debugMenu: DebugMenu = get()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
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
                )
            )
        }
        Button(
            onClick = {
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(configuration.workDayStartHour)
                    .setMinute(configuration.workDayStartMinute)
                    .setTitleText("Set work start time")
                    .build()
                    .apply {
                        addOnPositiveButtonClickListener {
                            updateConfiguration(
                                configuration.copy(
                                    workDayStartHour = hour,
                                    workDayStartMinute = minute
                                ).also(debugMenu::logConfigurationChangeEvent)
                            )
                        }
                    }
                    .show(fragmentManager, "timePicker")
            }
        ) {
            Text("Modify work start time")
        }
        Button(
            onClick = {
                // TODO: Work in progress. Don't forget to call debugMenu::logConfigurationChangeEvent
            }
        ) {
            Text("Modify day length (TODO)")
        }
        Button(
            onClick = {
                // TODO: Work in progress. Don't forget to call debugMenu::logConfigurationChangeEvent
            }
        ) {
            Text("Modify wage (TODO)")
        }
    }
}

private fun WageStatus.print(
    formatHoursMinutesAndSeconds: FormatHoursMinutesAndSecondsUseCase
) = when (this) {
    WageStatus.NotWorking -> "You are outside of your working hours."
    is WageStatus.Working -> " - Working for: ${formatHoursMinutesAndSeconds(elapsedSecondCount)}\n" +
            " - Remaining: ${formatHoursMinutesAndSeconds(remainingSecondCount)}\n" +
            " - Earned: $earnedWage"
}