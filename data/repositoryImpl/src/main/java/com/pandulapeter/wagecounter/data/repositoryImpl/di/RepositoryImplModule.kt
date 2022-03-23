package com.pandulapeter.wagecounter.data.repositoryImpl.di

import com.pandulapeter.wagecounter.data.repository.ConfigurationRepository
import com.pandulapeter.wagecounter.data.repositoryImpl.ConfigurationRepositoryImpl
import org.koin.dsl.module

val repositoryImplModule = module {
    single<ConfigurationRepository> { ConfigurationRepositoryImpl(get()) }
}