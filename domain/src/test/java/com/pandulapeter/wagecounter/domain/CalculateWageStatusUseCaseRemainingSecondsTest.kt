package com.pandulapeter.wagecounter.domain

import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.model.CurrencyFormat
import com.pandulapeter.wagecounter.data.model.WageStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.Calendar


class CalculateWageStatusUseCaseRemainingSecondsTest {

    private lateinit var sut: CalculateWageStatusUseCase
    private lateinit var sanitizedCalendar: Calendar
    private lateinit var configuration: Configuration

    @BeforeEach
    fun setUp() {
        sut = CalculateWageStatusUseCase(
            formatMonetaryAmount = FormatMonetaryAmountUseCase()
        )
        sanitizedCalendar = Calendar.getInstance().apply {
            set(Calendar.MILLISECOND, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.HOUR_OF_DAY, 0)
        }
        configuration = Configuration(
            hourlyWage = 0f,
            currencyFormat = CurrencyFormat.Prefix(""),
            workDayLengthInMinutes = 0,
            workDayStartHour = 0,
            workDayStartMinute = 0
        )
    }

    @DisplayName("WHEN one hour has passed THEN there are seven hours remaining")
    @Test
    fun verifySevenHoursRemaining() {
        val currentTimestamp = sanitizedCalendar.apply {
            set(Calendar.HOUR_OF_DAY, 10)
        }.timeInMillis
        val expected = 7 * 60 * 60
        val actual = (sut(
            currentTimestamp = currentTimestamp,
            configuration = configuration.copy(
                workDayLengthInMinutes = 8 * 60,
                workDayStartHour = 9
            )
        ) as? WageStatus.Working)?.remainingSecondCount
        Assertions.assertEquals(expected, actual)
    }

    @DisplayName("WHEN seven and a half hours has passed THEN there are thirty minutes remaining")
    @Test
    fun verifyThirtyMinutesRemaining() {
        val currentTimestamp = sanitizedCalendar.apply {
            set(Calendar.HOUR_OF_DAY, 16)
            set(Calendar.MINUTE, 30)
        }.timeInMillis
        val expected = 30 * 60
        val actual = (sut(
            currentTimestamp = currentTimestamp,
            configuration = configuration.copy(
                workDayLengthInMinutes = 8 * 60,
                workDayStartHour = 9
            )
        ) as? WageStatus.Working)?.remainingSecondCount
        Assertions.assertEquals(expected, actual)
    }
}