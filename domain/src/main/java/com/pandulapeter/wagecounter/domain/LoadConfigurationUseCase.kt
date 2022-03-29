package com.pandulapeter.wagecounter.domain

import com.pandulapeter.wagecounter.data.repository.ConfigurationRepository


class LoadConfigurationUseCase(
    private val configurationRepository: ConfigurationRepository
) {

    operator fun invoke() = configurationRepository.loadConfiguration()
}