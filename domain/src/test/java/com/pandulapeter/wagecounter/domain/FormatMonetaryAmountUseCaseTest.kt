package com.pandulapeter.wagecounter.domain

import com.pandulapeter.wagecounter.data.model.CurrencyFormat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


class FormatMonetaryAmountUseCaseTest {

    private lateinit var sut: FormatMonetaryAmountUseCase

    @BeforeEach
    fun setUp() {
        sut = FormatMonetaryAmountUseCase()
    }

    @DisplayName("WHEN a prefix is specified THEN it is displayed before the numeric value")
    @Test
    fun verifyPrefix() {
        val expected = "HUF 30.50"
        val actual = sut(
            currencyFormat = CurrencyFormat.Prefix("HUF "),
            amount = 30.5f
        )
        Assertions.assertEquals(expected, actual)
    }

    @DisplayName("WHEN a suffix is specified THEN it is displayed after the numeric value")
    @Test
    fun verifySuffix() {
        val expected = "55.00 €"
        val actual = sut(
            currencyFormat = CurrencyFormat.Suffix(" €"),
            amount = 55f
        )
        Assertions.assertEquals(expected, actual)
    }
}