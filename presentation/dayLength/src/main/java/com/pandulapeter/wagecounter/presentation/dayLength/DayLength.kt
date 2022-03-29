package com.pandulapeter.wagecounter.presentation.dayLength

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.domain.LoadConfigurationUseCase
import com.pandulapeter.wagecounter.presentation.debugMenu.DebugMenu
import org.koin.androidx.compose.get

@Composable
fun DayLength(
    fragmentManager: FragmentManager,
    onConfigurationChanged: (Configuration) -> Unit,
    showSnackbar: (String) -> Unit,
    loadConfiguration: LoadConfigurationUseCase = get(),
    debugMenu: DebugMenu = get()
) = Button(
    onClick = {
        debugMenu.logButtonPressEvent("Day length")
        val configuration = loadConfiguration()
        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(configuration.dayLengthInMinutes / MINUTES_IN_HOUR)
            .setMinute(configuration.dayLengthInMinutes % MINUTES_IN_HOUR)
            .setTitleText("Set day length")
            .build()
            .apply {
                addOnPositiveButtonClickListener {
                    val newDayLengthInMinutes = hour * MINUTES_IN_HOUR + minute
                    if (newDayLengthInMinutes != configuration.dayLengthInMinutes) {
                        onConfigurationChanged(
                            configuration.copy(
                                dayLengthInMinutes = newDayLengthInMinutes
                            )
                        )
                        showSnackbar("Day length updated")
                    }
                }
            }
            .show(fragmentManager, "dayLengthPicker")
    }
) {
    Text("Modify day length")
}

private const val MINUTES_IN_HOUR = 60