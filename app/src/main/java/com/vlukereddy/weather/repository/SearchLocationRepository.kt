package com.vlukereddy.weather.repository

import com.skydoves.sandwich.ApiResponse
import com.vlukereddy.weather.data.WeatherResponse

interface SearchLocationRepository {
    suspend fun search(q: String): ApiResponse<WeatherResponse>
}