package com.pandulapeter.wagecounter.data.model

data class Configuration(
    val hourlyWage: Float,
    val currencyPrefix: String,
    val currencySuffix: String,
    val workDayLengthInMinutes: Int,
    val workDayStartHour: Int,
    val workDayStartMinute: Int
)