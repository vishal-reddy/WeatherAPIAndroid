package com.vlukereddy.weather.module

import com.vlukereddy.weather.BuildConfig
import com.vlukereddy.weather.data.BaseURL
import com.vlukereddy.weather.repository.SearchLocationRepositoryImpl
import com.vlukereddy.weather.repository.SearchLocationRepository
import com.vlukereddy.weather.usecase.SearchLocationUseCase
import com.vlukereddy.weather.viewmodel.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient() {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
        }
    }

    single {
        Dispatchers.IO
    }

    single {
        BaseURL("${BuildConfig.WEATHER_API_BASE_URL}?key=${BuildConfig.WEATHER_API_KEY}")
    }
}

val repositoryModule = module {
    single<SearchLocationRepository> {
        SearchLocationRepositoryImpl(get(), get())
    }
}

val useCaseModule = module {
    single<SearchLocationUseCase> {
        SearchLocationUseCase(get(), get())
    }
}

val provideViewModelModule = module {
    viewModelOf(::HomeViewModel)
}

fun appModule() = listOf(provideViewModelModule, networkModule, repositoryModule, useCaseModule)