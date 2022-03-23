package com.pandulapeter.wagecounter.domain


class FormatTimeUseCase {

    operator fun invoke(
        minutesOrSeconds: Int
    ) = "${"%02d".format(minutesOrSeconds / 60)}:${"%02d".format(minutesOrSeconds % 60)}"
}