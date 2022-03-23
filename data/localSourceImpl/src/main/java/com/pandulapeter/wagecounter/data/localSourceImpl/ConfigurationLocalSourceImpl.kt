package com.pandulapeter.wagecounter.data.localSourceImpl

import com.pandulapeter.wagecounter.data.localSource.ConfigurationLocalSource
import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.model.CurrencyFormat

internal class ConfigurationLocalSourceImpl : ConfigurationLocalSource {

    // TODO: Replace with loading from local storage
    override fun loadConfiguration() = Configuration(
        hourlyWage = 15f,
        currencyFormat = CurrencyFormat.Suffix("$"),
        workDayLengthInMinutes = 8 * 60,
        workDayStartHour = 19,
        workDayStartMinute = 0
    )

    // TODO: Replace with saving to local storage
    override fun saveConfiguration(configuration: Configuration) = Unit
}