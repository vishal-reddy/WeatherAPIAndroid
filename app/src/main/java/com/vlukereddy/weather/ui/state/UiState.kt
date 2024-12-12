package com.vlukereddy.weather.ui.state

import com.vlukereddy.weather.ui.model.City

sealed class UiState {
    data object HomeEmpty: UiState()
    data object Loading: UiState()
    data class SearchResult(val input: String, val city: City?): UiState()
}