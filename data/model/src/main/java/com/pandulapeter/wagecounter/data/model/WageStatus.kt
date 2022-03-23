package com.pandulapeter.wagecounter.data.model

sealed class WageStatus {

    object NotWorking : WageStatus()

    data class Working(
        val elapsedSecondCount: Int,
        val earnedWage: String
    ) : WageStatus()
}