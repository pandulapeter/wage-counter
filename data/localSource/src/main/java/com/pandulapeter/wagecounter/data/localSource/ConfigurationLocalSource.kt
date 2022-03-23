package com.pandulapeter.wagecounter.data.localSource

import com.pandulapeter.wagecounter.data.model.Configuration

interface ConfigurationLocalSource {

    fun loadConfiguration(): Configuration?

    fun saveConfiguration(configuration: Configuration)
}