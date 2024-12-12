package com.vlukereddy.weather

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vlukereddy.weather.module.appModule
import com.vlukereddy.weather.ui.screen.HomeScreen
import com.vlukereddy.weather.ui.theme.WeatherAPIAppTheme
import com.vlukereddy.weather.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel


class MainActivity : ComponentActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val query = stringPreferencesKey("query")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinApplication(
                application = {
                    modules(appModule())
                }
            ) {
                val viewModel = koinViewModel<HomeViewModel>()

                viewModel.checkQueryString {
                    getQueryString()
                }

                WeatherAPIAppTheme {
                    HomeScreen(
                        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                        invoke = { uiEvent ->
                            viewModel.invoke(uiEvent, ::saveQueryString)
                        }
                    )
                }
            }
        }
    }

    private suspend fun saveQueryString(q: String) {
        dataStore.edit { settings ->
            settings[query] = q
        }
    }

    private suspend fun getQueryString(): Flow<String> {
        return dataStore.data.map { settings ->
            settings[query] ?: ""
        }
    }
}