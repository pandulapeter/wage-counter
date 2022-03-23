package com.pandulapeter.wagecounter.presentation.debugMenu

import android.app.Application
import com.pandulapeter.beagle.Beagle
import com.pandulapeter.beagle.modules.HeaderModule

internal class DebugMenuImpl : DebugMenu {

    override fun initialize(
        application: Application,
        appTitle: String,
        versionName: String
    ) {
        Beagle.initialize(application)
        Beagle.set(
            HeaderModule(
                title = appTitle,
                text = versionName
            )
        )
    }

}