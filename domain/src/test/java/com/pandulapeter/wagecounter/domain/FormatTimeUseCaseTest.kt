package com.pandulapeter.wagecounter.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


class FormatTimeUseCaseTest {

    private lateinit var sut: FormatTimeUseCase

    @BeforeEach
    fun setUp() {
        sut = FormatTimeUseCase()
    }

    @DisplayName("WHEN a known value is given THEN the formatter returns the proper String")
    @Test
    fun verifyKnownValues() {
        Assertions.assertEquals("00:00", sut(minutesOrSeconds = 0))
        Assertions.assertEquals("00:59", sut(minutesOrSeconds = 59))
        Assertions.assertEquals("01:00", sut(minutesOrSeconds = 60))
        Assertions.assertEquals("02:10", sut(minutesOrSeconds = 130))
    }
}