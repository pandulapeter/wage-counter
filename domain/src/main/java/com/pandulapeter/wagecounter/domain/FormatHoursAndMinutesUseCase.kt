package com.pandulapeter.wagecounter.domain

import java.util.Calendar


class FormatHoursAndMinutesUseCase {

    operator fun invoke(
        calendar: Calendar
    ) = "%02d".format(calendar.get(Calendar.HOUR_OF_DAY)) + ":" + "%02d".format(calendar.get(Calendar.MINUTE))
}