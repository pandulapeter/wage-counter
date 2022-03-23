package com.pandulapeter.wagecounter.data.localSourceImpl

import com.pandulapeter.wagecounter.data.localSource.ConfigurationLocalSource
import com.pandulapeter.wagecounter.data.model.Configuration

internal class ConfigurationLocalSourceImpl : ConfigurationLocalSource {

    // TODO: Implement loading from storage
    override fun loadConfiguration() = Configuration(
        hourlyWage = 0f,
        currencyPrefix = "Hello world!",
        currencySuffix = "",
        workDayLengthInMinutes = 0,
        workDayStartHour = 0,
        workDayStartMinute = 0
    )

    // TODO: Implement saving to storage
    override fun saveConfiguration(configuration: Configuration) = Unit
}