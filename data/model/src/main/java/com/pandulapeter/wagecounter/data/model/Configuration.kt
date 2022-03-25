package com.pandulapeter.wagecounter.data.model

data class Configuration(
    val startHour: Int,
    val startMinute: Int,
    val dayLengthInMinutes: Int,
    val hourlyWage: Float,
    val currencyFormat: CurrencyFormat
)