package com.pandulapeter.wagecounter.domain

import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.model.WorkStatus
import java.util.Calendar


class CalculateWorkStatusUseCase(
    private val formatMonetaryAmount: FormatMonetaryAmountUseCase
) {

    operator fun invoke(
        currentTimestamp: Long,
        configuration: Configuration
    ): WorkStatus {
        val lastWorkdayStartTimestamp = Calendar.getInstance().apply {
            timeInMillis = currentTimestamp
            set(Calendar.HOUR_OF_DAY, configuration.startHour)
            set(Calendar.MINUTE, configuration.startMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            while (timeInMillis > currentTimestamp) {
                add(Calendar.DAY_OF_YEAR, -1)
            }
        }.timeInMillis
        val millisecondsSinceLastWorkdayStarted = currentTimestamp - lastWorkdayStartTimestamp
        val secondsSinceLastWorkdayStarted = (millisecondsSinceLastWorkdayStarted / MILLIS_IN_SECOND).toInt()
        return if (millisecondsSinceLastWorkdayStarted > configuration.dayLengthInMinutes * MILLIS_IN_MINUTE) WorkStatus.NotWorking else WorkStatus.Working(
            elapsedSecondCount = secondsSinceLastWorkdayStarted,
            remainingSecondCount = configuration.dayLengthInMinutes * SECONDS_IN_MINUTE - secondsSinceLastWorkdayStarted,
            earned = formatMonetaryAmount(
                currencyFormat = configuration.currencyFormat,
                amount = (configuration.hourlyWage / MILLIS_IN_HOUR) * millisecondsSinceLastWorkdayStarted
            )
        )
    }

    companion object {
        private const val MILLIS_IN_SECOND = 1000L
        private const val SECONDS_IN_MINUTE = 60
        private const val MILLIS_IN_MINUTE = SECONDS_IN_MINUTE * MILLIS_IN_SECOND
        private const val MINUTES_IN_HOUR = 60
        private const val MILLIS_IN_HOUR = MILLIS_IN_MINUTE * MINUTES_IN_HOUR
    }
}