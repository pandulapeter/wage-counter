package com.pandulapeter.wagecounter.domain

import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.model.CurrencyFormat
import com.pandulapeter.wagecounter.data.model.WorkStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.Calendar


class CalculateWorkStatusUseCaseElapsedSecondsTest {

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

    @DisplayName("WHEN nine to five job is verified at 10 AM THEN working status is returned with 60 elapsed minutes")
    @Test
    fun verifyNineToFiveWorkingStatus() {
        val currentTimestamp = sanitizedCalendar.apply {
            set(Calendar.HOUR_OF_DAY, 10)
        }.timeInMillis
        val expected = 60 * 60
        val actual = (sut(
            currentTimestamp = currentTimestamp,
            configuration = configuration.copy(
                dayLengthInMinutes = 8 * 60,
                startHour = 9
            )
        ) as? WorkStatus.Working)?.elapsedSecondCount
        Assertions.assertEquals(expected, actual)
    }

    @DisplayName("WHEN nine to five job is verified at 10 PM THEN not working status is returned")
    @Test
    fun verifyNineToFiveNotWorkingStatusAfter() {
        val currentTimestamp = sanitizedCalendar.apply {
            set(Calendar.HOUR_OF_DAY, 22)
        }.timeInMillis
        val expected = WorkStatus.NotWorking
        val actual = sut(
            currentTimestamp = currentTimestamp,
            configuration = configuration.copy(
                dayLengthInMinutes = 8 * 60,
                startHour = 9
            )
        )
        Assertions.assertEquals(expected, actual)
    }

    @DisplayName("WHEN nine to five job is verified at 8 AM THEN not working status is returned")
    @Test
    fun verifyNineToFiveNotWorkingStatusBefore() {
        val currentTimestamp = sanitizedCalendar.apply {
            set(Calendar.HOUR_OF_DAY, 8)
        }.timeInMillis
        val expected = WorkStatus.NotWorking
        val actual = sut(
            currentTimestamp = currentTimestamp,
            configuration = configuration.copy(
                dayLengthInMinutes = 8 * 60,
                startHour = 9
            )
        )
        Assertions.assertEquals(expected, actual)
    }

    @DisplayName("WHEN nine to five job is verified at midnight THEN not working status is returned")
    @Test
    fun verifyNineToFiveNotWorkingStatusAtMidnight() {
        val currentTimestamp = sanitizedCalendar.timeInMillis
        val expected = WorkStatus.NotWorking
        val actual = sut(
            currentTimestamp = currentTimestamp,
            configuration = configuration.copy(
                dayLengthInMinutes = 8 * 60,
                startHour = 9
            )
        )
        Assertions.assertEquals(expected, actual)
    }

    @DisplayName("WHEN overnight job is verified at 11:30 PM THEN working status is returned with 90 elapsed minutes")
    @Test
    fun verifyOvernightWorkingStatus() {
        val currentTimestamp = sanitizedCalendar.apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 30)
        }.timeInMillis
        val expected = 90 * 60
        val actual = (sut(
            currentTimestamp = currentTimestamp,
            configuration = configuration.copy(
                dayLengthInMinutes = 8 * 60,
                startHour = 22
            )
        ) as? WorkStatus.Working)?.elapsedSecondCount
        Assertions.assertEquals(expected, actual)
    }

    @DisplayName("WHEN overnight job is verified at 11 AM THEN not working status is returned")
    @Test
    fun verifyOvernightNotWorkingStatusAfter() {
        val currentTimestamp = sanitizedCalendar.apply {
            set(Calendar.HOUR_OF_DAY, 11)
        }.timeInMillis
        val expected = WorkStatus.NotWorking
        val actual = sut(
            currentTimestamp = currentTimestamp,
            configuration = configuration.copy(
                dayLengthInMinutes = 8 * 60,
                startHour = 22
            )
        )
        Assertions.assertEquals(expected, actual)
    }

    @DisplayName("WHEN overnight job is verified at 8 AM THEN not working status is returned")
    @Test
    fun verifyOvernightNotWorkingStatusBefore() {
        val currentTimestamp = sanitizedCalendar.apply {
            set(Calendar.HOUR_OF_DAY, 8)
        }.timeInMillis
        val expected = WorkStatus.NotWorking
        val actual = sut(
            currentTimestamp = currentTimestamp,
            configuration = configuration.copy(
                dayLengthInMinutes = 8 * 60,
                startHour = 22
            )
        )
        Assertions.assertEquals(expected, actual)
    }

    @DisplayName("WHEN overnight job is verified at midnight THEN working status is returned with 120 elapsed minutes")
    @Test
    fun verifyOvernightNotWorkingStatusAtMidnight() {
        val currentTimestamp = sanitizedCalendar.timeInMillis
        val expected = 120 * 60
        val actual = (sut(
            currentTimestamp = currentTimestamp,
            configuration = configuration.copy(
                dayLengthInMinutes = 8 * 60,
                startHour = 22
            )
        ) as? WorkStatus.Working)?.elapsedSecondCount
        Assertions.assertEquals(expected, actual)
    }
}