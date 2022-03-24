package com.pandulapeter.wagecounter.data.localSourceImpl.di

import com.pandulapeter.wagecounter.data.localSource.ConfigurationLocalSource
import com.pandulapeter.wagecounter.data.localSourceImpl.ConfigurationLocalSourceImpl
import com.pandulapeter.wagecounter.data.localSourceImpl.LocalStorageManager
import com.pandulapeter.wagecounter.data.localSourceImpl.LocalStorageManagerImpl
import org.koin.dsl.module

val localSourceImplModule = module {
    factory<ConfigurationLocalSource> { ConfigurationLocalSourceImpl(get()) }
    single<LocalStorageManager> { LocalStorageManagerImpl(get()) }
}