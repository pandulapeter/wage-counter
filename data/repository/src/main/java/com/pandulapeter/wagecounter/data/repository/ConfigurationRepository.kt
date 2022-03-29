package com.pandulapeter.wagecounter.data.repository

import com.pandulapeter.wagecounter.data.model.Configuration

interface ConfigurationRepository {

    fun loadConfiguration(): Configuration

    fun saveConfiguration(configuration: Configuration)
}