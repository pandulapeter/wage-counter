package com.pandulapeter.wagecounter.data.repository

import com.pandulapeter.wagecounter.data.model.Configuration

interface ConfigurationRepository {

    fun getConfiguration(): Configuration?

    fun updateConfiguration(configuration: Configuration)
}