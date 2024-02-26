package com.example.privateflightsearchapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.privateflightsearchapp.data.Airport
import com.example.privateflightsearchapp.ui.theme.AppViewModelFactory
import com.example.privateflightsearchapp.ui.theme.PrivateFlightSearchAppTheme

@Composable
fun AllRoutesScreen() {
    val viewModel: AllRoutesViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        SearchField(
            value = viewModel.searchText,
            onValueChange = {
                viewModel.searchText = it
                viewModel.getSuggestions()
            },
            onSearch = {
                viewModel.getFlightPath()
            },
            modifier = Modifier
                .padding(10.dp)
//                .fillMaxWidth()
        )
        if(uiState.isSearching) {
            Card(
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    if(!uiState.searched) {
                        for(suggestion in uiState.searchSuggestions) {
                            Text(
                                text = suggestion,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(
                                        10.dp
                                    )
                                    .clickable {
                                        viewModel.searchText = suggestion
                                        viewModel.getFlightPath()
                                        viewModel.searchText = ""
                                    }
                            )
                        }
                    }
                }
            }
            FlightRoutesDisplay(paths = uiState.paths)
        } else {

            Spacer(modifier = Modifier.height(10.dp))
            AirportsDisplay(airports = uiState.airports)
        }
    }
}

@Composable
fun SearchField(
    onSearch: () -> Unit,
    onValueChange: (String) -> Unit,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
//            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
//                .fillMaxWidth()
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                decorationBox = { innerTextField ->
                    if(value.isEmpty()) {
                        Text(
                            text = "Search airport",
                            style = TextStyle(color = Color.Gray),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    innerTextField()

                },
                modifier = modifier
                    .weight(3f)
            )
//            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = onSearch,
                modifier = Modifier
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search flight"
                )
            }
        }
    }
}

@Composable
fun AirportsDisplay(
    airports: List<Airport>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(airports) { airport ->
            AirportCard(
                airport = airport,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 10.dp
                    )
            )
        }
    }
}

@Composable
fun AirportCard(
    airport: Airport,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .width(200.dp)
                ) {
                    Text(
                        text = airport.name,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier

                    )
                }
//                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = airport.passengers.toString(),
                    modifier = Modifier


                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = airport.iataCode,

            )
        }
    }
}

@Composable
fun FlightRoutesDisplay(
    paths: List<Path>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(paths) { path ->
            FlightRoute(
                path = path,
                modifier = Modifier
                    .padding(
                        top = 10.dp
                    )
            )
        }
    }
}

@Composable
fun FlightRoute(
    path: Path,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(20.dp)

            ) {
                Box(
                    modifier = Modifier

                        .weight(1f)
//                    .padding(
//                        end = 5.dp
//                    )

                ) {
                    Column {
                        Text(
                            text = path.from.name,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = path.from.passengers.toString()
                        )
                    }
                }
                Text(
                    text = "TO",
                    modifier = Modifier
                        .padding(10.dp)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)

                        .padding(
                            start = 5.dp
                        )
                ) {
                    Column {
                        Text(
                            text = path.to.name,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = path.to.passengers.toString()
                        )
                    }
                }
            }
            IconButton(
                onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Add to favorite routes"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllRoutesScreenPreview() {
    PrivateFlightSearchAppTheme {
        AllRoutesScreen()
    }
}