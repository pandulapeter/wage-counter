package com.pandulapeter.wagecounter.domain.di

import com.pandulapeter.wagecounter.domain.GetConfigurationUseCase
import com.pandulapeter.wagecounter.domain.UpdateConfigurationUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetConfigurationUseCase(get()) }
    factory { UpdateConfigurationUseCase(get()) }
}