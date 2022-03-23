package com.pandulapeter.wagecounter.data.repositoryImpl

import com.pandulapeter.wagecounter.data.localSource.ConfigurationLocalSource
import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.repository.ConfigurationRepository

class ConfigurationRepositoryImpl(
    private val configurationLocalSource: ConfigurationLocalSource
) : ConfigurationRepository {

    override fun getConfiguration() = configurationLocalSource.loadConfiguration()

    override fun updateConfiguration(configuration: Configuration) = configurationLocalSource.saveConfiguration(configuration)
}