package com.pandulapeter.wagecounter

import android.app.Application

class WageCounterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeDebugMenu()
    }

    private fun initializeDebugMenu() {
        // TODO inject the DebugMenu interface, call its initialize() function
    }
}