package com.pandulapeter.wagecounter.presentation.debugMenu.di

import com.pandulapeter.wagecounter.presentation.debugMenu.DebugMenu
import com.pandulapeter.wagecounter.presentation.debugMenu.DebugMenuImpl
import org.koin.dsl.module

val debugMenuModule = module {
    single<DebugMenu> { DebugMenuImpl(get(), get(), get()) }
}