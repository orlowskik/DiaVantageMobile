package com.example.diavantagemobile.ui.physicians

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diavantagemobile.ui.theme.DiaVantageMobileTheme
import com.example.diavantagemobile.util.CreateTopAppBar
import com.example.diavantagemobile.util.ScreenScaffoldTemplate
import com.example.diavantagemobile.util.api.physicians.PhysiciansRepository
import com.example.diavantagemobile.util.api.responses.AddressSerializer
import com.example.diavantagemobile.util.api.responses.PhysicianResponse
import com.example.diavantagemobile.util.api.responses.UserSerializer
import com.example.diavantagemobile.util.composables.FilteringBar
import com.example.diavantagemobile.util.composables.SearchBar
import com.example.diavantagemobile.util.data.TopAppBarTypes
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun PhysiciansScreen(
    physiciansViewModel: PhysiciansViewModel = viewModel(),
    physiciansRepository: PhysiciansRepository,
    modifier: Modifier = Modifier,
    returnToHome: () -> Unit
){

    val physicians by physiciansViewModel.physicians.collectAsState()
    val isSearching by physiciansViewModel.isSearching.collectAsState()
    val searchText by physiciansViewModel.searchText.collectAsState()

    LaunchedEffect(true){
        physiciansViewModel.reloadPhysicians(physiciansRepository)
    }

    ScreenScaffoldTemplate(
        topBar = {
            CreateTopAppBar(
                title = "Physicians",
                appBarType = TopAppBarTypes.SmallTopAppBar,
                actions = {
                    SearchBar(
                        searchText = searchText,
                        onSearchTextChange = physiciansViewModel::onSearchTextChange,
                        modifier = modifier
                    )
                },
                navigationIcon = {
                    IconButton (onClick = { returnToHome()} ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                modifier = modifier
            )
        },
        content = {
            PhysiciansContentLayout(
                physicians = physicians,
                inputSort = physiciansViewModel.inputSorting,
                sortingMap = physiciansViewModel.sortingMap,
                onSortingChange = fun(sorting: Int){
                    physiciansViewModel.updateSorting(sorting)
                    physiciansViewModel.sortPhysicians()
                },
                isSearching = isSearching,
                modifier = modifier
            )
        }
    )
}



@Composable
fun PhysiciansContentLayout(
    physicians: List<PhysicianResponse>,
    inputSort: Int,
    sortingMap: Map<Int, String>,
    onSortingChange: (Int) -> Unit,
    isSearching: Boolean = false,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .padding(15.dp)
    ) {
        FilteringBar(
            inputSort = inputSort,
            sortingMap = sortingMap,
            onSortingChange = onSortingChange,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(),
            isExpanded = false,
            options = listOf(),
            optionsStates = mapOf()
        )
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())

        ) {
            if(isSearching){
                Box(
                    modifier = modifier.fillMaxSize()
                ){
                    CircularProgressIndicator(
                        modifier = modifier
                            .align(Alignment.Center)
                    )
                }
            } else{
                if(physicians.isEmpty()){
                    Box(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(top = 25.dp)
                    ) {
                        Text(
                            text = "No physicians found",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = modifier
                                .align(Alignment.Center)
                        )
                    }
                } else {
                    physicians.forEach() {
                        PhysicianCard(
                            physician = it,
                            modifier = modifier
                                .padding(bottom = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PhysicianCard(
    isExpanded: Boolean = false,
    physician: PhysicianResponse,
    modifier: Modifier = Modifier
){
    var expanded by remember { mutableStateOf(isExpanded) }

    Card(
        modifier = modifier
            .height(IntrinsicSize.Min),
        shape = RoundedCornerShape(bottomStart = 16.dp, topEnd = 16.dp)
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .height(IntrinsicSize.Min)
                .padding(5.dp)
        ){
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                Row (){
                    PhysicianIcon()
                    PhysicianName(
                        name = physician.user.first_name.toString() + " " + physician.user.last_name.toString(),
                        specialty = physician.specialty,

                    )
                }
                PhysicianItemButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded},
                )
            }
            if (expanded) {
                HorizontalDivider(
                    modifier = modifier
                        .fillMaxWidth(0.85f)
                        .padding(top = 5.dp, bottom = 5.dp)
                )
                PhysicianDetails(
                    physician = physician,
                    modifier = modifier
                )
            }
        }
    }
}



@Composable
private fun PhysicianIcon(
    modifier: Modifier = Modifier
){
    Icon(
        imageVector = Icons.Filled.AssignmentInd,
        contentDescription = "Physician profile icon",
        tint = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .size(80.dp)
    )
}


@Composable
private fun PhysicianName(
    name: String,
    specialty: String,
    modifier: Modifier = Modifier

){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxHeight()
    ){
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier
        )
        Text(
            text = specialty,
            style = MaterialTheme.typography.bodyLarge,
            fontStyle = FontStyle.Italic,
            modifier = modifier
                .padding(start = 5.dp)
        )
    }
}



@Composable
private fun PhysicianItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxHeight()
            .padding(end = 10.dp)
    ) {
        Icon(
            imageVector = if(expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = "Expand physician info",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = modifier
                .size(40.dp)
        )
    }
}

@Composable
private fun PhysicianDetails(
    physician: PhysicianResponse,
    modifier: Modifier = Modifier,
){
    val propertyWidth = 75.dp
    Column(
        modifier = modifier
            .padding(horizontal = 30.dp)
            .padding(bottom = 15.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Email: ",
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier
                    .width(propertyWidth)
            )
            Text(
                text = physician.user.email,
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier
            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Phone: ",
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier
                    .width(propertyWidth)
            )
            Text(
                text = physician.phone,
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier
            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Address: ",
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier
                    .width(propertyWidth)
            )
            Text(
                text = physician.address.createAddress(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier
                    .padding(top = 2.dp)
            )
        }
    }
}


val physician = PhysicianResponse(
    user = UserSerializer(
        username = "jank",
        password = "dsa",
        email = "jank@example.com",
        first_name = "Jan",
        last_name = "Kowalski"
    ),
    address = AddressSerializer(
        country = "Poland",
        state = "Lower Silesia",
        city = "Wrocław",
        street = "Kwiatowa",
        zip_code = "54-117",
        number = "12",
        apartment = null
    ),
    specialty = "Diabetology",
    phone = "123456789"
)


val physiciansList = listOf(
    PhysicianResponse(
        user = UserSerializer(
            username = "jank",
            password = "dsa",
            email = "jank@example.com",
            first_name = "Jan",
            last_name = "Kowalski"
        ),
        address = AddressSerializer(
            country = "Poland",
            state = "Lower Silesia",
            city = "Wrocław",
            street = "Kwiatowa",
            zip_code = "54-117",
            number = "12",
            apartment = null
        ),
        specialty = "Diabetology",
        phone = "123456789"
    ),
    PhysicianResponse(
        user = UserSerializer(
            username = "dank",
            password = "dsa",
            email = "dank@example.com",
            first_name = "Daniel",
            last_name = "Kordecki"
        ),
        address = AddressSerializer(
            country = "Poland",
            state = "Lower Silesia",
            city = "Wrocław",
            street = "Maślicka",
            zip_code = "54-117",
            number = "19",
            apartment = "20"
        ),
        specialty = "Cardiology",
        phone = "987567123",
    )
)

@Composable
@Preview(showBackground = true)
fun PhysicianCardPreview(){

    DiaVantageMobileTheme {
        PhysicianCard(
            isExpanded = false,
            physician = physician,
            modifier = Modifier
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PhysicianCardFullPreview(){


    DiaVantageMobileTheme {
        PhysicianCard(
            isExpanded = true,
            physician = physician,
            modifier = Modifier
        )
    }
}





@Composable
@Preview(showBackground = true)
fun PhysiciansLayoutPreview(){

    DiaVantageMobileTheme {

        ScreenScaffoldTemplate(
            topBar = {
                CreateTopAppBar(
                    title = "Physicians",
                    appBarType = TopAppBarTypes.SmallTopAppBar,
                    actions = {
                        SearchBar(
                            searchText = "Asd",
                            onSearchTextChange = {}
                        )
                    },
                    navigationIcon = {
                        IconButton (onClick = { } ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    },
                    modifier = Modifier
                )
            },
            content = {
                PhysiciansContentLayout(
                    physicians = physiciansList,
                    inputSort = PhysiciansViewModel().inputSorting,
                    sortingMap = PhysiciansViewModel().sortingMap,
                    onSortingChange = {},
                    isSearching = false,
                    modifier = Modifier
                )
            }
        )
    }
}
