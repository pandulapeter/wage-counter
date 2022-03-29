package com.pandulapeter.wagecounter.domain

import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.repository.ConfigurationRepository


class SaveConfigurationUseCase(
    private val configurationRepository: ConfigurationRepository
) {

    operator fun invoke(configuration: Configuration) = configurationRepository.saveConfiguration(configuration)
}