# Weather App Sample

This is a sample weather app written in Jetpack compose.

## Setup

1. Get an API key from weatherapi.com
2. Insert into gradle.properties `weather.api.key=`
3. Run the app in Android Studio

## Principles + Stack

* This project was completed in about five hours
* There is a preference for KMP libraries where possible even though this is not a KMP project

1. Uses some clean architecture principles
2. Dependency Injection in Koin
3. Jetpack Compose for the UI
4. Jetpack Data Store for Preferences
5. Ktor + Kotlin Serialization for API requests
6. Sandwich for Network State Management
7. Coil for Image loading
8. UiState pattern is used to trigger composable screens
9. UiEvent pattern is used as an MVI like + MVVM hybrid scenario - This pattern drastically reduces callback management code in nested composables
10. Data Store is used to persist successful location responses so that when you go back to the app after closing it, it refreshes the latest data for that location

## Departures from original figma design

* The figma did not use any tokens so I decided to just default to what made sense to me
* I ran out of time to fix some colors so some future iteration will have to be done to do all that