package com.pandulapeter.wagecounter.presentation.shared.uiComponents

import androidx.compose.material.ScaffoldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ScaffoldState.showSnackbar(
    coroutineScope: CoroutineScope,
    message: String
) {
    snackbarHostState.currentSnackbarData?.dismiss()
    coroutineScope.launch {
        snackbarHostState.showSnackbar(
            message = message
        )
    }
}