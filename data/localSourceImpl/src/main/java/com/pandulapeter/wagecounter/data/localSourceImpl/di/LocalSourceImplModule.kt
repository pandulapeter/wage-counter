package com.pandulapeter.wagecounter.data.localSourceImpl.di

import com.pandulapeter.wagecounter.data.localSource.ConfigurationLocalSource
import com.pandulapeter.wagecounter.data.localSourceImpl.ConfigurationLocalSourceImpl
import org.koin.dsl.module

val localSourceImplModule = module {
    factory<ConfigurationLocalSource> { ConfigurationLocalSourceImpl() }
}