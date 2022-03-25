package com.pandulapeter.wagecounter.presentation.startTime

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.pandulapeter.wagecounter.domain.GetConfigurationUseCase
import com.pandulapeter.wagecounter.domain.UpdateConfigurationUseCase
import com.pandulapeter.wagecounter.presentation.debugMenu.DebugMenu
import org.koin.androidx.compose.get

@Composable
fun StartTime(
    fragmentManager: FragmentManager,
    getConfiguration: GetConfigurationUseCase = get(),
    updateConfiguration: UpdateConfigurationUseCase = get(),
    debugMenu: DebugMenu = get()
) = Button(
    onClick = {
        val configuration = getConfiguration()
        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(configuration.startHour)
            .setMinute(configuration.startMinute)
            .setTitleText("Set work start time")
            .build()
            .apply {
                addOnPositiveButtonClickListener {
                    updateConfiguration(
                        configuration.copy(
                            startHour = hour,
                            startMinute = minute
                        ).also(debugMenu::logConfigurationChangeEvent)
                    )
                }
            }
            .show(fragmentManager, "timePicker")
    }
) {
    Text("Modify work start time")
}