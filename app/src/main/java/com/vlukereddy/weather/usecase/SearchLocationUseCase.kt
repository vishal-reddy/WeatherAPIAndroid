package com.vlukereddy.weather.usecase

import com.vlukereddy.weather.repository.SearchLocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SearchLocationUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val searchLocationRepository: SearchLocationRepository) {

    suspend operator fun invoke(q: String) =
        withContext(dispatcher) {
            return@withContext searchLocationRepository.search(q)
        }
}