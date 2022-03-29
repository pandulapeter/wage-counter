package com.pandulapeter.wagecounter.presentation.main

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.domain.LoadConfigurationUseCase
import com.pandulapeter.wagecounter.domain.SaveConfigurationUseCase
import com.pandulapeter.wagecounter.presentation.dayLength.DayLength
import com.pandulapeter.wagecounter.presentation.debugMenu.DebugMenu
import com.pandulapeter.wagecounter.presentation.hourlyWage.HourlyWage
import com.pandulapeter.wagecounter.presentation.shared.WageCounterTheme
import com.pandulapeter.wagecounter.presentation.shared.showSnackbar
import com.pandulapeter.wagecounter.presentation.startTime.StartTime
import com.pandulapeter.wagecounter.presentation.summary.Summary
import org.koin.androidx.compose.get

@Composable
fun Main(
    fragmentManager: FragmentManager,
    loadConfiguration: LoadConfigurationUseCase = get(),
    saveConfiguration: SaveConfigurationUseCase = get(),
    debugMenu: DebugMenu = get()
) {
    // Initialize the auto refresh logic
    val currentTimestamp = remember { mutableStateOf(0L) }
    AutoRefresh { currentTimestamp.value = it }

    // Setup configuration handling
    val currentConfiguration = remember { mutableStateOf(loadConfiguration()) }
    fun updateAndSaveConfiguration(newConfiguration: Configuration) {
        currentConfiguration.value = newConfiguration
        saveConfiguration(newConfiguration)
        debugMenu.logConfigurationChangeEvent(newConfiguration)
    }

    // Setup the UI
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    fun showSnackbar(message: String) = scaffoldState.showSnackbar(
        coroutineScope = coroutineScope,
        message = message
    )
    Container(
        scaffoldState = scaffoldState
    ) {
        Summary(
            currentTimestamp = currentTimestamp.value,
            configuration = currentConfiguration.value
        )
        StartTime(
            fragmentManager = fragmentManager,
            onConfigurationChanged = ::updateAndSaveConfiguration,
            showSnackbar = ::showSnackbar
        )
        DayLength(
            onConfigurationChanged = ::updateAndSaveConfiguration,
            showSnackbar = ::showSnackbar
        )
        HourlyWage(
            onConfigurationChanged = ::updateAndSaveConfiguration,
            showSnackbar = ::showSnackbar
        )
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
    scaffoldState: ScaffoldState,
    content: @Composable ColumnScope.() -> Unit
) {
    WageCounterTheme {
        Scaffold(
            modifier = Modifier,
            scaffoldState = scaffoldState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                content = content
            )
        }
    }
}