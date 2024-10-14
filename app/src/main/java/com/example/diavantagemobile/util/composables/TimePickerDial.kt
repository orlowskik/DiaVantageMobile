@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.diavantagemobile.util.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.util.Calendar


@Composable
fun TimePickerField(
    inputTime: String,
    currentTime: Calendar = Calendar.getInstance(),
    onTimeChange: (TimePickerState?) -> Unit,
    modifier: Modifier = Modifier
){
    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    OutlinedTextField(
        value = inputTime,
        onValueChange = { onTimeChange(timePickerState) },
        label = { Text("Measurement Time")},
        placeholder = { Text("HH:mm") },
        trailingIcon = {
            Icon(
                Icons.Default.AccessTime,
                contentDescription = "Select time"
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(inputTime) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showTimePicker = true
                    }
                }
            }
    )

    if (showTimePicker) {
        AdvancedTimePicker(
            timePickerState = timePickerState,
            onDismiss = {showTimePicker = false},
            onTimeSelected = onTimeChange,
            modifier = modifier
        )
    }

}



@Composable
fun AdvancedTimePicker(
    timePickerState: TimePickerState,
    onTimeSelected: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {

    var showDial by remember { mutableStateOf(true) }


    val toggleIcon = if (showDial) {
        Icons.Filled.EditCalendar
    } else {
        Icons.Filled.AccessTime
    }

    TimePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onTimeSelected(timePickerState)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        modifier = modifier,
        toggle = {
            IconButton(onClick = { showDial = !showDial }) {
                Icon(
                    imageVector = toggleIcon,
                    contentDescription = "Time picker type toggle",
                )
            }
        },
    ) {
        if (showDial) {
            TimePicker(
                state = timePickerState,
            )
        } else {
            TimeInput(
                state = timePickerState,
            )
        }
    }

}


@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier =
            modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = modifier.weight(1f))
                    dismissButton?.invoke()
                    confirmButton()
                }
            }
        }
    }
}