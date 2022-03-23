package com.pandulapeter.wagecounter

import android.app.Application
import com.pandulapeter.wagecounter.data.localSourceImpl.di.localSourceImplModule
import com.pandulapeter.wagecounter.data.repositoryImpl.di.repositoryImplModule
import com.pandulapeter.wagecounter.domain.di.domainModule
import com.pandulapeter.wagecounter.presentation.debugMenu.DebugMenu
import com.pandulapeter.wagecounter.presentation.debugMenu.di.debugMenuModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WageCounterApplication : Application() {

    private val debugMenu by inject<DebugMenu>()

    override fun onCreate() {
        super.onCreate()
        initializeDependencyInjection()
        initializeDebugMenu()
    }

    private fun initializeDependencyInjection() {
        startKoin {
            androidContext(this@WageCounterApplication)
            modules(
                localSourceImplModule +
                        repositoryImplModule +
                        domainModule +
                        debugMenuModule
            )
        }
    }

    private fun initializeDebugMenu() = debugMenu.initialize(
        application = this,
        appTitle = getString(R.string.wage_counter),
        versionName = BuildConfig.VERSION_NAME
    )
}