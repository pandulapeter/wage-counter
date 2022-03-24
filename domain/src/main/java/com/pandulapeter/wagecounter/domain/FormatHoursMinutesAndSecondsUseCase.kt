package com.pandulapeter.wagecounter.domain

import java.util.Calendar
import java.util.TimeZone


class FormatHoursMinutesAndSecondsUseCase(
    private val formatHoursAndMinutes: FormatHoursAndMinutesUseCase
) {

    operator fun invoke(
        calendar: Calendar
    ) = formatHoursAndMinutes(calendar) + ":" + "%02d".format(calendar.get(Calendar.SECOND))

    operator fun invoke(
        secondCount: Int
    ) = invoke(
        Calendar.getInstance().apply {
            timeInMillis = -TimeZone.getDefault().rawOffset.toLong()
            add(Calendar.SECOND, secondCount)
        }
    )
}