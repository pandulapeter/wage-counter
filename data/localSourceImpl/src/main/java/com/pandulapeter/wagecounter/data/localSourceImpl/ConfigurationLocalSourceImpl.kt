package com.pandulapeter.wagecounter.data.localSourceImpl

import com.pandulapeter.wagecounter.data.localSource.ConfigurationLocalSource
import com.pandulapeter.wagecounter.data.model.Configuration

internal class ConfigurationLocalSourceImpl(
    private val localStorageManager: LocalStorageManager
) : ConfigurationLocalSource {

    override fun loadConfiguration() = localStorageManager.savedConfiguration

    override fun saveConfiguration(configuration: Configuration) {
        localStorageManager.savedConfiguration = configuration
    }
}