package com.pandulapeter.wagecounter.domain

import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.repository.ConfigurationRepository


class UpdateConfigurationUseCase(
    private val configurationRepository: ConfigurationRepository
) {

    operator fun invoke(configuration: Configuration) = configurationRepository.updateConfiguration(configuration)
}