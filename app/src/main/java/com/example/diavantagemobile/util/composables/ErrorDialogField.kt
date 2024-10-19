package com.example.diavantagemobile.util.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.diavantagemobile.ui.interfaces.APIViewModel


@Composable
fun ErrorDialogField(
    title: String = "Error",
    apiViewModel: APIViewModel,
    modifier: Modifier = Modifier,
){
    if (apiViewModel.showErrorDialog){
        CustomDialog(
            title = title,
            onDismissRequest = {
                apiViewModel.toggleErrorDialog()
                apiViewModel.resetError()
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Column (
                  horizontalAlignment = Alignment.CenterHorizontally,
                  modifier = modifier
                      .fillMaxWidth()
            ) {
                apiViewModel.errorName?.let {
                    Text(
                        text = "Error",
                        style = MaterialTheme.typography.displayLarge
                    )
                }
                apiViewModel.errorMessage?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
