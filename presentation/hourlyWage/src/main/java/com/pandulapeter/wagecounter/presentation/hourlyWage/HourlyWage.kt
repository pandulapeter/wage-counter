package com.pandulapeter.wagecounter.presentation.hourlyWage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.domain.LoadConfigurationUseCase
import com.pandulapeter.wagecounter.presentation.debugMenu.DebugMenu
import org.koin.androidx.compose.get

@Composable
fun HourlyWage(
    isDialogOpen: Boolean,
    setIsDialogOpen: (Boolean) -> Unit,
    onConfigurationChanged: (Configuration) -> Unit,
    showSnackbar: (String) -> Unit,
    debugMenu: DebugMenu = get()
) {
    Button(
        onClick = {
            debugMenu.logButtonPressEvent("Hourly wage")
            setIsDialogOpen(true)
        }
    ) {
        Text("Modify hourly wage")
    }
    if (isDialogOpen) {
        HourlyWagePickerDialog(
            onConfigurationChanged = onConfigurationChanged,
            showSnackbar = showSnackbar,
            dismissDialog = { setIsDialogOpen(false) }
        )
    }
}

@Composable
private fun HourlyWagePickerDialog(
    onConfigurationChanged: (Configuration) -> Unit,
    showSnackbar: (String) -> Unit,
    dismissDialog: () -> Unit,
    getConfiguration: LoadConfigurationUseCase = get()
) = AlertDialog(
    modifier = Modifier
        .width(320.dp), // TODO: The background color is inconsistent with the time picker dialog
    onDismissRequest = dismissDialog,
    title = {
        // TODO: The vertical padding and the font color are inconsistent with the time picker dialog
        Text(
            text = "Set hourly wage",
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight.SemiBold
        )
    },
    text = {
        Text("Work in progress")
    },
    buttons = {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                modifier = Modifier.wrapContentWidth(),
                onClick = dismissDialog
            ) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(
                modifier = Modifier.wrapContentWidth(),
                onClick = {
                    // TODO: Work in progress. Call onConfigurationChanged()
                    showSnackbar("Work in progress")
                    dismissDialog()
                }
            ) {
                Text("OK")
            }
        }
    }
)