package com.pandulapeter.wagecounter.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


class FormatWorkingHoursUseCaseTest {

    private lateinit var sut: FormatWorkingHoursUseCase

    @BeforeEach
    fun setUp() {
        sut = FormatWorkingHoursUseCase(
            formatHoursAndMinutes = FormatHoursAndMinutesUseCase()
        )
    }

    @DisplayName("WHEN a known value is given THEN the formatter returns the proper String")
    @Test
    fun verifyKnownValues() {
        Assertions.assertEquals(
            "00:00 - 00:02", sut(
                workDayLengthInMinutes = 2,
                workDayStartHour = 0,
                workDayStartMinute = 0
            )
        )
        Assertions.assertEquals(
            "01:00 - 06:36", sut(
                workDayLengthInMinutes = 5 * 60 + 36,
                workDayStartHour = 1,
                workDayStartMinute = 0
            )
        )
        Assertions.assertEquals(
            "09:00 - 17:00", sut(
                workDayLengthInMinutes = 8 * 60,
                workDayStartHour = 9,
                workDayStartMinute = 0
            )
        )
    }
}