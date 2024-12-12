package com.vlukereddy.weather.repository

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import com.vlukereddy.weather.data.BaseURL
import com.vlukereddy.weather.data.WeatherResponse
import io.ktor.client.HttpClient

class SearchLocationRepositoryImpl(private val client: HttpClient, private val baseURL: BaseURL): SearchLocationRepository {
    override suspend fun search(q: String): ApiResponse<WeatherResponse> {
        val url = "${baseURL.url}&q=${q}"

        return client.getApiResponse<WeatherResponse>(url)
    }
}