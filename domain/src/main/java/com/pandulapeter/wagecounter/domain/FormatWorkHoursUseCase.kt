package com.pandulapeter.wagecounter.domain

import java.util.Calendar


class FormatWorkHoursUseCase(
    private val formatHoursAndMinutes: FormatHoursAndMinutesUseCase
) {

    operator fun invoke(
        workDayLengthInMinutes: Int,
        workDayStartHour: Int,
        workDayStartMinute: Int
    ): String {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.MILLISECOND, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, workDayStartMinute)
            set(Calendar.HOUR_OF_DAY, workDayStartHour)
        }
        val startTime = formatHoursAndMinutes(calendar)
        calendar.add(Calendar.MINUTE, workDayLengthInMinutes)
        val endTime = formatHoursAndMinutes(calendar)
        return "$startTime - $endTime"
    }
}