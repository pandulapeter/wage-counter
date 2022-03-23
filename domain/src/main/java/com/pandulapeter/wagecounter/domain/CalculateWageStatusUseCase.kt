package com.pandulapeter.wagecounter.domain

import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.model.WageStatus
import java.util.Calendar


class CalculateWageStatusUseCase(
    private val formatMonetaryAmount: FormatMonetaryAmountUseCase
) {

    operator fun invoke(
        currentTimestamp: Long,
        configuration: Configuration
    ): WageStatus {
        val lastWorkdayStartTimestamp = Calendar.getInstance().apply {
            timeInMillis = currentTimestamp
            set(Calendar.HOUR_OF_DAY, configuration.workDayStartHour)
            set(Calendar.MINUTE, configuration.workDayStartMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            while (timeInMillis > currentTimestamp) {
                add(Calendar.DAY_OF_YEAR, -1)
            }
        }.timeInMillis
        val secondsSinceLastWorkdayStarted = ((currentTimestamp - lastWorkdayStartTimestamp) / MILLIS_IN_SECOND).toInt()
        return if (secondsSinceLastWorkdayStarted > configuration.workDayLengthInMinutes * SECONDS_IN_MINUTE) WageStatus.NotWorking else WageStatus.Working(
            elapsedSecondCount = secondsSinceLastWorkdayStarted,
            earnedWage = formatMonetaryAmount(
                currencyFormat = configuration.currencyFormat,
                amount = (configuration.hourlyWage / SECONDS_IN_HOUR) * secondsSinceLastWorkdayStarted
            )
        )
    }

    companion object {
        private const val MILLIS_IN_SECOND = 1000L
        private const val SECONDS_IN_MINUTE = 60
        private const val SECONDS_IN_HOUR = SECONDS_IN_MINUTE * 60
    }
}