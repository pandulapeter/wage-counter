package com.pandulapeter.wagecounter.presentation.main

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import com.pandulapeter.wagecounter.presentation.dayLength.DayLength
import com.pandulapeter.wagecounter.presentation.hourlyWage.HourlyWage
import com.pandulapeter.wagecounter.presentation.shared.WageCounterTheme
import com.pandulapeter.wagecounter.presentation.startTime.StartTime
import com.pandulapeter.wagecounter.presentation.summary.Summary

@Composable
fun Main(
    fragmentManager: FragmentManager
) {
    val currentTimestamp = remember { mutableStateOf(0L) }
    AutoRefresh { currentTimestamp.value = it }
    WageCounterTheme {
        Container {
            Summary(currentTimestamp = currentTimestamp.value)
            StartTime(fragmentManager = fragmentManager)
            DayLength()
            HourlyWage()
        }
    }
}

private const val REFRESH_PERIOD = 1000L

@Composable
private fun AutoRefresh(
    updateTimestamp: (Long) -> Unit
) = DisposableEffect(Unit) {
    val handler = Handler(Looper.getMainLooper())
    val runnable = object : Runnable {
        override fun run() {
            updateTimestamp(System.currentTimeMillis())
            handler.postDelayed(this, REFRESH_PERIOD)
        }
    }
    handler.post(runnable)
    onDispose { handler.removeCallbacks(runnable) }
}

@Composable
private fun Container(
    content: @Composable ColumnScope.() -> Unit
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    content = content
)