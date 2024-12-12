package com.vlukereddy.weather.ui.event

import com.vlukereddy.weather.ui.model.City

sealed interface UiEvent {
    data class Search(val q: String): UiEvent
    data class Save(val city: City): UiEvent
}