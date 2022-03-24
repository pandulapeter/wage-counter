package com.pandulapeter.wagecounter.presentation.debugMenu

import android.app.Application
import com.pandulapeter.beagle.Beagle
import com.pandulapeter.beagle.common.configuration.Placement
import com.pandulapeter.beagle.modules.HeaderModule
import com.pandulapeter.beagle.modules.KeyValueListModule
import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.model.WageStatus
import com.pandulapeter.wagecounter.domain.FormatHoursMinutesAndSecondsUseCase
import com.pandulapeter.wagecounter.domain.FormatMonetaryAmountUseCase
import com.pandulapeter.wagecounter.domain.FormatWorkingHoursUseCase
import java.util.Calendar

internal class DebugMenuImpl(
    private val formatMonetaryAmount: FormatMonetaryAmountUseCase,
    private val formatWorkingHours: FormatWorkingHoursUseCase,
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
            )
        )
    }

    override fun updateData(
        currentTimestamp: Long,
        configuration: Configuration,
        wageStatus: WageStatus
    ) = Beagle.add(
        KeyValueListModule(
            id = "configuration",
            title = "Configuration",
            isExpandedInitially = true,
            pairs = listOf(
                "Hours" to formatWorkingHours(
                    workDayLengthInMinutes = configuration.workDayLengthInMinutes,
                    workDayStartHour = configuration.workDayStartHour,
                    workDayStartMinute = configuration.workDayStartMinute
                ),
                "Wage" to formatMonetaryAmount(
                    currencyFormat = configuration.currencyFormat,
                    amount = configuration.hourlyWage
                )
            )
        ),
        KeyValueListModule(
            id = "wageStatus",
            title = "Wage Status",
            isExpandedInitially = true,
            pairs = buildList {
                add(
                    "Time" to formatHoursMinutesAndSeconds(
                        Calendar.getInstance().apply {
                            timeInMillis = currentTimestamp
                        }
                    )
                )
                val isWorking = wageStatus is WageStatus.Working
                add("Working" to if (isWorking) "Yes" else "No")
                if (isWorking) {
                    wageStatus as WageStatus.Working
                    add("Elapsed" to formatHoursMinutesAndSeconds(wageStatus.elapsedSecondCount))
                    add("Remaining" to formatHoursMinutesAndSeconds(wageStatus.remainingSecondCount))
                    add("Earned" to wageStatus.earnedWage)
                }
            }
        ),
        placement = Placement.Bottom
    )
}