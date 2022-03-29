package com.pandulapeter.wagecounter.presentation.main

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun Main(
    lifecycle: Lifecycle,
    fragmentManager: FragmentManager,
    loadConfiguration: LoadConfigurationUseCase = get(),
    saveConfiguration: SaveConfigurationUseCase = get(),
    debugMenu: DebugMenu = get()
) {
    // Initialize the auto refresh logic
    val coroutineScope = rememberCoroutineScope()
    val currentTimestamp = remember { mutableStateOf(0L) }
    coroutineScope.AutoRefreshTimestamp(lifecycle) { currentTimestamp.value = it }

    // Setup configuration handling
    val currentConfiguration = remember { mutableStateOf(loadConfiguration()) }
    fun updateAndSaveConfiguration(newConfiguration: Configuration) {
        currentConfiguration.value = newConfiguration
        saveConfiguration(newConfiguration)
        debugMenu.logConfigurationChangeEvent(newConfiguration)
    }

    // Setup the UI
    val scaffoldState = rememberScaffoldState()
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
private fun CoroutineScope.AutoRefreshTimestamp(
    lifecycle: Lifecycle,
    updateTimestamp: (Long) -> Unit
) = DisposableEffect(Unit) {
    var job: Job? = null
    launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            job?.cancel()
            job = launch {
                while (isActive) {
                    updateTimestamp(System.currentTimeMillis())
                    delay(REFRESH_PERIOD)
                }
            }
        }
    }
    onDispose { job?.cancel() }
}

@Composable
private fun Container(
    scaffoldState: ScaffoldState,
    content: @Composable ColumnScope.() -> Unit
) = WageCounterTheme {
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