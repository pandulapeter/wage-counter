package com.pandulapeter.wagecounter.data.repositoryImpl

import com.pandulapeter.wagecounter.data.localSource.ConfigurationLocalSource
import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.repository.ConfigurationRepository

internal class ConfigurationRepositoryImpl(
    private val configurationLocalSource: ConfigurationLocalSource
) : ConfigurationRepository {

    override fun loadConfiguration() = configurationLocalSource.loadConfiguration()

    override fun saveConfiguration(configuration: Configuration) = configurationLocalSource.saveConfiguration(configuration)
}