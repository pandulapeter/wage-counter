package com.pandulapeter.wagecounter.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.Calendar


class FormatHoursAndMinutesUseCaseTest {

    private lateinit var sut: FormatHoursAndMinutesUseCase
    private lateinit var sanitizedCalendar: Calendar

    @BeforeEach
    fun setUp() {
        sut = FormatHoursAndMinutesUseCase()
        sanitizedCalendar = Calendar.getInstance().apply {
            set(Calendar.MILLISECOND, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.HOUR_OF_DAY, 0)
        }
    }

    @DisplayName("WHEN a known value is given THEN the formatter returns the proper String")
    @Test
    fun verifyKnownValue() {
        Assertions.assertEquals(
            "05:48", sut(
                sanitizedCalendar.apply {
                    set(Calendar.HOUR_OF_DAY, 5)
                    set(Calendar.MINUTE, 48)
                }
            )
        )
    }
}