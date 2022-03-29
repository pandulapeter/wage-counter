package com.pandulapeter.wagecounter.presentation.summary

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.model.WorkStatus
import com.pandulapeter.wagecounter.domain.CalculateWorkStatusUseCase
import com.pandulapeter.wagecounter.domain.FormatHoursMinutesAndSecondsUseCase
import com.pandulapeter.wagecounter.domain.FormatMonetaryAmountUseCase
import com.pandulapeter.wagecounter.domain.FormatWorkHoursUseCase
import com.pandulapeter.wagecounter.presentation.debugMenu.DebugMenu
import com.pandulapeter.wagecounter.presentation.shared.R
import com.pandulapeter.wagecounter.presentation.shared.uiComponents.HighlightedCard
import com.pandulapeter.wagecounter.presentation.shared.uiComponents.LabeledValueIndicator
import org.koin.androidx.compose.get

@Composable
fun Summary(
    currentTimestamp: Long,
    configuration: Configuration
) = Column {
    Header()
    Spacer(modifier = Modifier.size(16.dp))
    SummaryContent(
        currentTimestamp = currentTimestamp,
        configuration = configuration
    )
}

@Composable
private fun Header(
    context: Context = get()
) = HighlightedCard {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight(),
            painter = painterResource(R.drawable.ic_wage_counter),
            contentDescription = "",
            contentScale = ContentScale.Inside
        )
        Text(
            modifier = Modifier
                .wrapContentHeight(Alignment.CenterVertically)
                .fillMaxWidth(),
            text = context.getString(R.string.wage_counter),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.on_primary)
        )
    }
}

@Composable
private fun SummaryContent(
    currentTimestamp: Long,
    configuration: Configuration,
    formatMonetaryAmount: FormatMonetaryAmountUseCase = get(),
    formatWorkHours: FormatWorkHoursUseCase = get()
) = Column(
    modifier = Modifier.padding(bottom = 8.dp)
) {
    Text(
        modifier = Modifier.padding(
            bottom = 8.dp,
            start = 16.dp,
            end = 16.dp
        ),
        text = "Your schedule is ${
            formatWorkHours(
                workDayLengthInMinutes = configuration.dayLengthInMinutes,
                workDayStartHour = configuration.startHour,
                workDayStartMinute = configuration.startMinute
            )
        } with an hourly wage of ${
            formatMonetaryAmount(
                currencyFormat = configuration.currencyFormat,
                amount = configuration.hourlyWage
            )
        }."
    )
    WorkStatusIndicator(
        currentTimestamp = currentTimestamp,
        configuration = configuration
    )
}

@Composable
private fun WorkStatusIndicator(
    currentTimestamp: Long,
    configuration: Configuration,
    formatHoursMinutesAndSeconds: FormatHoursMinutesAndSecondsUseCase = get(),
    calculateWorkStatus: CalculateWorkStatusUseCase = get(),
    debugMenu: DebugMenu = get()
) = when (val workStatus = calculateWorkStatus(
    currentTimestamp = currentTimestamp,
    configuration = configuration
).also { wageStatus ->
    debugMenu.updateData(
        currentTimestamp = currentTimestamp,
        configuration = configuration,
        workStatus = wageStatus
    )
}) {
    WorkStatus.NotWorking -> Text(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 8.dp
        ),
        text = "You are outside of your working hours."
    )
    is WorkStatus.Working -> Column {
        LabeledValueIndicator(
            label = "Working for",
            value = formatHoursMinutesAndSeconds(workStatus.elapsedSecondCount)
        )
        LabeledValueIndicator(
            label = "Remaining",
            value = formatHoursMinutesAndSeconds(workStatus.remainingSecondCount)
        )
        LabeledValueIndicator(
            label = "Earned",
            value = workStatus.earned
        )
    }
}