package com.pandulapeter.wagecounter.domain

import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.model.CurrencyFormat
import com.pandulapeter.wagecounter.data.model.WageStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.Calendar


class CalculateWageStatusUseCaseEarnedWageTest {

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

    @DisplayName("WHEN nine to five job is verified at 10:30 AM with RON 60 wage THEN working status is returned with the properly formatted string")
    @Test
    fun verifyPrefix() {
        val currentTimestamp = sanitizedCalendar.apply {
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 30)
        }.timeInMillis
        val expected = "RON 90.00"
        val actual = (sut(
            currentTimestamp = currentTimestamp,
            configuration = configuration.copy(
                currencyFormat = CurrencyFormat.Prefix("RON "),
                hourlyWage = 60f,
                workDayLengthInMinutes = 8 * 60,
                workDayStartHour = 9
            )
        ) as? WageStatus.Working)?.earnedWage
        Assertions.assertEquals(expected, actual)
    }

    @DisplayName("WHEN nine to five job is verified at 11 AM with 120$ wage THEN working status is returned with the properly formatted string")
    @Test
    fun verifySuffix() {
        val currentTimestamp = sanitizedCalendar.apply {
            set(Calendar.HOUR_OF_DAY, 11)
        }.timeInMillis
        val expected = "240.00$"
        val actual = (sut(
            currentTimestamp = currentTimestamp,
            configuration = configuration.copy(
                currencyFormat = CurrencyFormat.Suffix("$"),
                hourlyWage = 120f,
                workDayLengthInMinutes = 8 * 60,
                workDayStartHour = 9
            )
        ) as? WageStatus.Working)?.earnedWage
        Assertions.assertEquals(expected, actual)
    }
}