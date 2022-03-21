package com.pandulapeter.wagecounter

import android.app.Application
import com.pandulapeter.beagle.Beagle
import com.pandulapeter.beagle.modules.HeaderModule

class WageCounterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeDebugMenu()
    }

    private fun initializeDebugMenu() {
        Beagle.initialize(this)
        Beagle.set(
            HeaderModule(
                title = getString(R.string.wage_counter),
                text = BuildConfig.VERSION_NAME
            )
        )
    }
}