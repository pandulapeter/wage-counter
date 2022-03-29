package com.pandulapeter.wagecounter.presentation.hourlyWage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.model.CurrencyFormat
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
) {
    val configuration = getConfiguration()
    val currencyPrefix = remember { mutableStateOf(configuration.currencyFormat.let { if (it is CurrencyFormat.Prefix) it.data else "" }) }
    val currencyValue = remember { mutableStateOf("%.2f".format(configuration.hourlyWage)) }
    val currencySuffix = remember { mutableStateOf(configuration.currencyFormat.let { if (it is CurrencyFormat.Suffix) it.data else "" }) }
    AlertDialog(
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
            Column(
                modifier = Modifier.padding(top = 32.dp) // TODO: Top padding not taken into consideration
            ) {
                TextInputField(
                    label = "Currency prefix",
                    value = currencyPrefix.value,
                    onValueChange = {
                        currencyPrefix.value = it
                        if (it.isNotBlank()) {
                            currencySuffix.value = ""
                        }
                    }
                )
                TextInputField(
                    label = "Hourly wage",
                    value = currencyValue.value,
                    isNumbersOnly = true,
                    onValueChange = { currencyValue.value = it }
                )
                TextInputField(
                    label = "Currency suffix",
                    value = currencySuffix.value,
                    onValueChange = {
                        currencySuffix.value = it
                        if (it.isNotBlank()) {
                            currencyPrefix.value = ""
                        }
                    }
                )
            }
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
                        val newConfiguration = configuration.copy(
                            currencyFormat = if (currencyPrefix.value.isNotBlank()) {
                                CurrencyFormat.Prefix(currencyPrefix.value)
                            } else {
                                CurrencyFormat.Suffix(currencySuffix.value)
                            },
                            hourlyWage = currencyValue.value.toFloatOrNull() ?: configuration.hourlyWage
                        )
                        if (configuration != newConfiguration) {
                            onConfigurationChanged(newConfiguration)
                            showSnackbar("Hourly wage updated")
                        }
                        dismissDialog()
                    }
                ) {
                    Text("OK")
                }
            }
        }
    )
}

@Composable
private fun TextInputField(
    label: String,
    value: String,
    isNumbersOnly: Boolean = false,
    onValueChange: (String) -> Unit
) = TextField(
    modifier = Modifier.padding(top = 16.dp),
    value = value,
    keyboardOptions = KeyboardOptions(
        autoCorrect = false,
        keyboardType = if (isNumbersOnly) KeyboardType.Number else KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    singleLine = true,
    onValueChange = onValueChange,
    label = { Text(text = label) }
)