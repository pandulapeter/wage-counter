package com.pandulapeter.wagecounter.presentation.debugMenu

import android.app.Application
import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.model.WorkStatus

interface DebugMenu {

    fun initialize(
        application: Application,
        appTitle: String,
        versionName: String
    ) = Unit

    fun updateData(
        currentTimestamp: Long,
        configuration: Configuration,
        workStatus: WorkStatus
    ) = Unit

    fun logConfigurationChangeEvent(
        newConfiguration: Configuration
    ) = Unit
}