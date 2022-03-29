package com.pandulapeter.wagecounter.presentation.debugMenu

import android.app.Application
import com.pandulapeter.beagle.Beagle
import com.pandulapeter.beagle.common.configuration.Placement
import com.pandulapeter.beagle.modules.HeaderModule
import com.pandulapeter.beagle.modules.KeyValueListModule
import com.pandulapeter.beagle.modules.LogListModule
import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.model.WorkStatus
import com.pandulapeter.wagecounter.domain.FormatHoursMinutesAndSecondsUseCase
import com.pandulapeter.wagecounter.domain.FormatMonetaryAmountUseCase
import com.pandulapeter.wagecounter.domain.FormatWorkHoursUseCase
import java.util.Calendar

internal class DebugMenuImpl(
    private val formatMonetaryAmount: FormatMonetaryAmountUseCase,
    private val formatWorkHours: FormatWorkHoursUseCase,
    private val formatHoursMinutesAndSeconds: FormatHoursMinutesAndSecondsUseCase
) : DebugMenu {

    override fun initialize(
        application: Application,
        appTitle: String,
        versionName: String
    ) {
        Beagle.initialize(application)
        Beagle.set(
            HeaderModule(
                title = appTitle,
                text = versionName
            ),
            LogListModule(
                isExpandedInitially = true
            )
        )
        Beagle.log("App started")
    }

    override fun updateData(
        currentTimestamp: Long,
        configuration: Configuration,
        workStatus: WorkStatus
    ) = Beagle.add(
        KeyValueListModule(
            id = "configuration",
            title = "Configuration",
            isExpandedInitially = true,
            pairs = listOf(
                "Hours" to formatWorkHours(
                    workDayLengthInMinutes = configuration.dayLengthInMinutes,
                    workDayStartHour = configuration.startHour,
                    workDayStartMinute = configuration.startMinute
                ),
                "Hourly wage" to formatMonetaryAmount(
                    currencyFormat = configuration.currencyFormat,
                    amount = configuration.hourlyWage
                )
            )
        ),
        KeyValueListModule(
            id = "workStatus",
            title = "Work status",
            isExpandedInitially = true,
            pairs = buildList {
                add(
                    "Time" to formatHoursMinutesAndSeconds(
                        Calendar.getInstance().apply {
                            timeInMillis = currentTimestamp
                        }
                    )
                )
                val isWorking = workStatus is WorkStatus.Working
                add("Working" to if (isWorking) "Yes" else "No")
                if (isWorking) {
                    workStatus as WorkStatus.Working
                    add("Elapsed" to formatHoursMinutesAndSeconds(workStatus.elapsedSecondCount))
                    add("Remaining" to formatHoursMinutesAndSeconds(workStatus.remainingSecondCount))
                    add("Earned" to workStatus.earned)
                }
            }
        ),
        placement = Placement.Below(HeaderModule.ID)
    )

    override fun logConfigurationChangeEvent(newConfiguration: Configuration) = Beagle.log(
        "Configuration changed: " + formatWorkHours(
            workDayLengthInMinutes = newConfiguration.dayLengthInMinutes,
            workDayStartHour = newConfiguration.startHour,
            workDayStartMinute = newConfiguration.startMinute
        ) + " for " + formatMonetaryAmount(
            currencyFormat = newConfiguration.currencyFormat,
            amount = newConfiguration.hourlyWage
        )
    )
}