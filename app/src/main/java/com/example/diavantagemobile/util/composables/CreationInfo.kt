package com.example.diavantagemobile.util.composables


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diavantagemobile.ui.theme.DiaVantageMobileTheme
import com.example.diavantagemobile.util.api.responses.FailedSendBloodResponse
import com.example.diavantagemobile.util.api.responses.FailedSendGlucoseResponse


@Composable
fun CreationInfo(
    glucoseResponse: FailedSendGlucoseResponse? = null,
    bloodResponse: FailedSendBloodResponse? = null,
    showDialog: Boolean = true,
    onDismissRequest: () -> Unit = {},
    title: String,
    modifier: Modifier = Modifier
){

    if (showDialog){
        CustomDialog(
            title = title,
            onDismissRequest = onDismissRequest,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
        ){
            CreateContent(
                glucoseResponse = glucoseResponse,
                bloodResponse = bloodResponse,
                modifier = modifier
            )
        }
    }
}

@Composable
fun CreateContent(
    glucoseResponse: FailedSendGlucoseResponse? = null,
    bloodResponse: FailedSendBloodResponse? = null,
    modifier: Modifier
){
    if ((glucoseResponse == null) and (bloodResponse == null)){
        Text(
            text = "Creation successful",
            style = MaterialTheme.typography.displayLarge
        )
    } else {
        if (glucoseResponse != null) {
            CreateGlucoseResponseText(
                glucoseResponse = glucoseResponse,
                modifier = modifier,
            )
        }
        if (bloodResponse != null) {
            CreateBloodResponseText(
                bloodResponse = bloodResponse,
                modifier = modifier,
            )
        }
    }
}

@Composable
fun CreateBloodResponseText(
    bloodResponse: FailedSendBloodResponse,
    modifier: Modifier = Modifier
){
    val keys = bloodResponse.getFieldsKeys()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ){
        for ( field in bloodResponse.javaClass.declaredFields ){
            field.isAccessible = true;
            if (field.name in keys){
                field.get(bloodResponse)?.let {
                    Row (
                        horizontalArrangement = Arrangement.Start,
                        modifier = modifier
                            .fillMaxWidth(),
                    ){
                        Text(
                            text = "${field.name}: ",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = it.toString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }

}


@Composable
fun CreateGlucoseResponseText(
    glucoseResponse: FailedSendGlucoseResponse,
    modifier: Modifier = Modifier
){

    val keys = glucoseResponse.getFieldsKeys()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ){
        for ( field in glucoseResponse.javaClass.declaredFields ){
            field.isAccessible = true;
            if (field.name in keys){
                field.get(glucoseResponse)?.let {
                    Row (
                        horizontalArrangement = Arrangement.Start,
                        modifier = modifier
                            .fillMaxWidth(),
                    ){
                        Text(
                            text = "${field.name}: ",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = it.toString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}






@Composable
@Preview(showBackground = true)
fun FailedCreationPreview(){
    DiaVantageMobileTheme {
        CreationInfo(
            title = "Failed creation PopUp",
            modifier = Modifier,
        )
    }
}