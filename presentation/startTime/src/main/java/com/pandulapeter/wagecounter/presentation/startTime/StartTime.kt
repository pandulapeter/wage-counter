package com.pandulapeter.wagecounter.presentation.startTime

import android.content.Context
import android.text.format.DateFormat
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.domain.LoadConfigurationUseCase
import org.koin.androidx.compose.get

@Composable
fun StartTime(
    fragmentManager: FragmentManager,
    onConfigurationChanged: (Configuration) -> Unit,
    showSnackbar: (String) -> Unit,
    context: Context = get(),
    loadConfiguration: LoadConfigurationUseCase = get()
) = Button(
    onClick = {
        val configuration = loadConfiguration()
        MaterialTimePicker.Builder()
            .setTimeFormat(if (DateFormat.is24HourFormat(context)) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H)
            .setHour(configuration.startHour)
            .setMinute(configuration.startMinute)
            .setTitleText("Set work start time")
            .build()
            .apply {
                addOnPositiveButtonClickListener {
                    if (hour != configuration.startHour || minute != configuration.startMinute) {
                        onConfigurationChanged(
                            configuration.copy(
                                startHour = hour,
                                startMinute = minute
                            )
                        )
                        showSnackbar("Work start time updated")
                    }
                }
            }
            .show(fragmentManager, "timePicker")
    }
) {
    Text("Modify work start time")
}