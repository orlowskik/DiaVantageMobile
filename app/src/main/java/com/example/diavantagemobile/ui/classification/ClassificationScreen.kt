package com.example.diavantagemobile.ui.classification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diavantagemobile.ui.classification.questionaire.Question
import com.example.diavantagemobile.ui.classification.questionaire.Questionnaire
import com.example.diavantagemobile.ui.theme.DiaVantageMobileTheme
import com.example.diavantagemobile.util.CreateTopAppBar
import com.example.diavantagemobile.util.ScreenScaffoldTemplate
import com.example.diavantagemobile.util.data.TopAppBarTypes
import kotlin.math.roundToInt

@Composable
fun ClassificationScreen(
    classificationViewModel: ClassificationViewModel = viewModel(),
    question: Question?,
    questionId: Int = 0,
    modifier: Modifier = Modifier,
    prevScreen: (Int) -> Unit,
    nxtScreen: (Int) -> Unit,
    returnHome: () -> Unit,
){
    ScreenScaffoldTemplate(
        topBar = {
            CreateTopAppBar(
                title = "Classification Form",
                appBarType = TopAppBarTypes.SmallTopAppBar,
                actions = {},
                navigationIcon = {
                    IconButton(onClick = {returnHome()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                modifier = modifier,
            )
        },
        content = {
            ClassificationContentLayout(
                question = question,
                nxtScreen = nxtScreen,
                prevScreen = prevScreen,
                questionId = questionId,
                returnHome = returnHome
            )
        }
    )
}

@Composable
fun ClassificationContentLayout(
    question: Question?,
    questionId: Int,
    prevScreen: (Int) -> Unit,
    nxtScreen: (Int) -> Unit,
    returnHome: () -> Unit,
    modifier: Modifier = Modifier
){
    if (questionId == 0){
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Text(
                "Diabetes Classification Form",
                style = MaterialTheme.typography.titleLarge
            )
            Button(
                onClick = { nxtScreen(1) },
                modifier = modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(top = 25.dp)
            ){
                Text(
                    "Start",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    } else if(question == null) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Text(
                "Form filled",
                style = MaterialTheme.typography.titleLarge
            )
            Button(
                onClick = returnHome,
                modifier = modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(top = 25.dp)
            ){
                Text(
                    "Submit",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    } else{
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .verticalScroll(rememberScrollState())
        ) {
            Question(
                question = question,
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            ){
                Button(
                    onClick = { prevScreen(questionId - 1) },
                    modifier = modifier
                        .height(75.dp)
                        .width(200.dp)
                        .padding(top = 25.dp)
                ){
                    Text(
                        "Previous question",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                Button(
                    onClick = { nxtScreen(questionId + 1) },
                    modifier = modifier
                        .height(75.dp)
                        .width(200.dp)
                        .padding(top = 25.dp)
                ){
                    Text(
                        "Next question",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

            }
        }
    }
}



@Composable
fun Question(
    question: Question,
    modifier: Modifier = Modifier,
){
   Column (
       modifier = modifier,
       horizontalAlignment = Alignment.CenterHorizontally
   ){
       Box (
           modifier = Modifier
               .padding(20.dp)
               .fillMaxWidth()
               .wrapContentSize(Alignment.Center)
       ){
           Text(
               text = question.questionText,
               textAlign = TextAlign.Center,
               style = MaterialTheme.typography.titleLarge
           )
       }

       Box(
       ){
           if (question.answers.isEmpty()){
               question.args?.let {
                   if (it.type == "SliderInput"){
                       SliderInput(
                           valueRange = it.range,
                           steps = it.steps
                       )
                   } else if (it.type == "DecimalInput"){
                       TextFieldInput()

                   }
               }
           } else {
               RadioButtonGroup(
                   options = question.answers
               )
           }
       }
   }
}


@Composable
fun TextFieldInput(
){
    var textValue by remember { mutableStateOf("") }

    TextField(
        value = textValue,
        onValueChange = { textValue = it },
        placeholder = { Text("Insert value") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )
}

@Composable
fun SliderInput(
    valueRange: ClosedFloatingPointRange<Float>?,
    steps: Int? = 10
){
    var sliderPosition by remember { mutableFloatStateOf(0f) }




    Column (
        modifier = Modifier
            .padding(10.dp)
    ){
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            steps = steps ?: 10,
            valueRange = valueRange ?: 0f..10f
        )
        Text(
            text = sliderPosition.roundToInt().toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun RadioButtonGroup(
    options: ArrayList<String> = ArrayList()
){
    var selectedOption by remember { mutableIntStateOf(0) }

    Column(){
        for ((index, value) in options.withIndex()){
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == index,
                    onClick = { selectedOption = index }
                )
                Text(
                    value,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun QuestionPreview(){
    val questionnaire = Questionnaire
    questionnaire.initQuestions()
    DiaVantageMobileTheme {
        Question(
            question = questionnaire.questions[14]
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ClassificationScreenPreview(){
    val questionnaire = Questionnaire
    questionnaire.initQuestions()

    DiaVantageMobileTheme {
        ClassificationScreen(
            question = questionnaire.questions[0],
            questionId = 1,
            modifier = Modifier,
            prevScreen = {},
            nxtScreen = {},
        ) { }
    }
}
