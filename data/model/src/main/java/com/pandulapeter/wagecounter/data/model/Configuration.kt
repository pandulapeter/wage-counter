package com.pandulapeter.wagecounter.data.model

data class Configuration(
    val hourlyWage: Float,
    val currencyFormat: CurrencyFormat,
    val workDayLengthInMinutes: Int,
    val workDayStartHour: Int,
    val workDayStartMinute: Int
)