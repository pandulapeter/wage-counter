package com.pandulapeter.wagecounter.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.Calendar


class FormatHoursMinutesAndSecondsUseCaseTest {

    private lateinit var sut: FormatHoursMinutesAndSecondsUseCase
    private lateinit var sanitizedCalendar: Calendar

    @BeforeEach
    fun setUp() {
        sut = FormatHoursMinutesAndSecondsUseCase(
            formatHoursAndMinutes = FormatHoursAndMinutesUseCase()
        )
        sanitizedCalendar = Calendar.getInstance().apply {
            set(Calendar.MILLISECOND, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.HOUR_OF_DAY, 0)
        }
    }

    @DisplayName("WHEN a known value is given THEN the formatter returns the proper String for the primary signature")
    @Test
    fun verifyKnownValuePrimarySignature() {
        Assertions.assertEquals(
            "09:22:17", sut(
                sanitizedCalendar.apply {
                    set(Calendar.HOUR_OF_DAY, 9)
                    set(Calendar.MINUTE, 22)
                    set(Calendar.SECOND, 17)
                }
            )
        )
    }

    @DisplayName("WHEN a known value is given THEN the formatter returns the proper String for the secondary signature")
    @Test
    fun verifyKnownValueSecondarySignature() {
        Assertions.assertEquals("00:01:07", sut(67))
        Assertions.assertEquals("00:02:13", sut(133))
    }
}