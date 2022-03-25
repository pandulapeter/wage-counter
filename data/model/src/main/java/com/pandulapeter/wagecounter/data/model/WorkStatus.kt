package com.pandulapeter.wagecounter.data.model

sealed class WorkStatus {

    object NotWorking : WorkStatus()

    data class Working(
        val elapsedSecondCount: Int,
        val remainingSecondCount: Int,
        val earned: String
    ) : WorkStatus()
}