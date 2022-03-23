package com.pandulapeter.wagecounter.presentation.debugMenu

import android.app.Application

interface DebugMenu {

    fun initialize(
        application: Application,
        appTitle: String,
        versionName: String
    ) = Unit
}