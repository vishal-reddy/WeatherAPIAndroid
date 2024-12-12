package com.vlukereddy.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.ApiResponse
import com.vlukereddy.weather.ui.event.UiEvent
import com.vlukereddy.weather.ui.state.UiState
import com.vlukereddy.weather.usecase.SearchLocationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.vlukereddy.weather.data.toCity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

class HomeViewModel(
    val searchLocationUseCase: SearchLocationUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.HomeEmpty)
    val uiState: StateFlow<UiState> get() = _uiState

    private suspend fun search(q: String, saveDataStore: suspend (String) -> Unit) {
        _uiState.emit(UiState.Loading)

        when(val result = searchLocationUseCase(q)) {
            is ApiResponse.Failure.Error -> {
                _uiState.emit(UiState.SearchResult(q, null))
            }
            is ApiResponse.Failure.Exception -> {
                _uiState.emit(UiState.SearchResult(q, null))
            }
            is ApiResponse.Success -> {
                _uiState.emit(UiState.SearchResult(q, result.data.toCity()))
                saveDataStore(q)
            }
        }
    }

    fun invoke(uiEvent: UiEvent, saveDataStore: suspend (String) -> Unit = {}) = viewModelScope.launch {
        when (uiEvent) {
            is UiEvent.Save -> {}
            is UiEvent.Search -> {
                search(uiEvent.q, saveDataStore)
            }
        }
    }

    fun checkQueryString(getQueryString: suspend () -> Flow<String>) = viewModelScope.launch {
        getQueryString().collectLatest {
            if (it.isNotBlank()) {
                invoke(UiEvent.Search(it))
            }
        }
    }
}
