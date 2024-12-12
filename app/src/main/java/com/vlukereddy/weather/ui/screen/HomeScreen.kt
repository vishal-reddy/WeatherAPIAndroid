package com.vlukereddy.weather.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vlukereddy.weather.R
import com.vlukereddy.weather.ui.event.UiEvent
import com.vlukereddy.weather.ui.state.UiState
import com.vlukereddy.weather.ui.theme.WeatherAPIAppTheme

@Composable
fun HomeScreen(uiState: UiState, invoke: (UiEvent) -> Unit = {}) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxHeight()
            ) {
                // TODO debouce if time is available
                Spacer(modifier = Modifier.padding(24.dp))

                var input by remember { mutableStateOf(if (uiState is UiState.SearchResult) uiState.input else "") }

                TextField(
                    value = input, onValueChange = {
                        input = it
                        invoke(UiEvent.Search(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    trailingIcon = {
                        Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search))
                    },
                    placeholder = {
                        Text(stringResource(R.string.search_location))
                    }
                )

                when (uiState) {
                    UiState.HomeEmpty -> EmptyCities()

                    UiState.Loading -> Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }

                    is UiState.SearchResult -> CitiesSearch(uiState, invoke)
                }
            }
        }
    }
}

@Composable
private fun EmptyCities() {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp), horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.padding(64.dp))

        Text(stringResource(R.string.no_city_selected), style = MaterialTheme.typography.headlineLarge)

        Text(stringResource(R.string.please_search_for_a_city), style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
fun CitiesSearch(uiState: UiState.SearchResult, invoke: (UiEvent) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)) {
        if (uiState.city != null) {
            Column {
                Spacer(modifier = Modifier.padding(24.dp))

                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    val url = "https:${uiState.city.condition.iconURL}"

                    var loadingImage by remember { mutableStateOf(false) }

                    if (loadingImage) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            CircularProgressIndicator()
                        }
                    }

                    AsyncImage(
                        model = url,
                        contentDescription = uiState.city.condition.description,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .size(128.dp),
                        onLoading = {
                            loadingImage = true
                        },
                        onSuccess = {
                            loadingImage = false
                        },
                        onError = {
                            loadingImage = false
                        }
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(uiState.city.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text("${uiState.city.temperature.toInt()}°", style = MaterialTheme.typography.headlineLarge)
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Column {
                    Card(
                        onClick = {
                            invoke(UiEvent.Save(city = uiState.city))
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(24.dp)
                                .fillMaxWidth()
                        ) {
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(stringResource(R.string.humidity), style = MaterialTheme.typography.labelLarge)
                                    Text("${uiState.city.humidity}%", style = MaterialTheme.typography.labelLarge)
                                }

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(stringResource(R.string.uv), style = MaterialTheme.typography.labelLarge)
                                    Text(uiState.city.uvIndex.toInt().toString(), style = MaterialTheme.typography.labelLarge)
                                }

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(stringResource(R.string.feels_like), style = MaterialTheme.typography.labelLarge)
                                    Text("${uiState.city.temperatureFeelsLike.toInt()}°", style = MaterialTheme.typography.labelLarge)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenEmptyPreview() {
    WeatherAPIAppTheme {
        HomeScreen(UiState.HomeEmpty)
    }
}

@Composable
@PreviewLightDark
@PreviewScreenSizes
@PreviewFontScale
fun HomeScreenAllVariationsPreview() {
    WeatherAPIAppTheme {
        HomeScreen(UiState.HomeEmpty)
    }
}