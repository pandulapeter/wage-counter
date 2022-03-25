package com.pandulapeter.wagecounter.domain

import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.model.CurrencyFormat
import com.pandulapeter.wagecounter.data.model.WorkStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.Calendar


class CalculateWorkStatusUseCaseRemainingSecondsTest {

    private lateinit var sut: CalculateWorkStatusUseCase
    private lateinit var sanitizedCalendar: Calendar
    private lateinit var configuration: Configuration

    @BeforeEach
    fun setUp() {
        sut = CalculateWorkStatusUseCase(
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
            dayLengthInMinutes = 0,
            startHour = 0,
            startMinute = 0
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
                dayLengthInMinutes = 8 * 60,
                startHour = 9
            )
        ) as? WorkStatus.Working)?.remainingSecondCount
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
                dayLengthInMinutes = 8 * 60,
                startHour = 9
            )
        ) as? WorkStatus.Working)?.remainingSecondCount
        Assertions.assertEquals(expected, actual)
    }
}