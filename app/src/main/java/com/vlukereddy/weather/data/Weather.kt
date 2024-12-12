package com.vlukereddy.weather.data

import com.vlukereddy.weather.ui.model.City
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val location: Location,
    val current: Current
)

@Serializable
data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    @SerialName("tz_id") val tzId: String,
    @SerialName("localtime_epoch") val localtimeEpoch: Long,
    val localtime: String
)

@Serializable
data class Current(
    @SerialName("last_updated_epoch") val lastUpdatedEpoch: Long,
    @SerialName("last_updated") val lastUpdated: String,
    @SerialName("temp_c") val tempC: Double,
    @SerialName("temp_f") val tempF: Double,
    @SerialName("is_day") val isDay: Int,
    val condition: Condition,
    @SerialName("wind_mph") val windMph: Double,
    @SerialName("wind_kph") val windKph: Double,
    @SerialName("wind_degree") val windDegree: Int,
    @SerialName("wind_dir") val windDir: String,
    @SerialName("pressure_mb") val pressureMb: Double,
    @SerialName("pressure_in") val pressureIn: Double,
    @SerialName("precip_mm") val precipMm: Double,
    @SerialName("precip_in") val precipIn: Double,
    val humidity: Int,
    val cloud: Int,
    @SerialName("feelslike_c") val feelslikeC: Double,
    @SerialName("feelslike_f") val feelslikeF: Double,
    @SerialName("windchill_c") val windchillC: Double,
    @SerialName("windchill_f") val windchillF: Double,
    @SerialName("heatindex_c") val heatindexC: Double,
    @SerialName("heatindex_f") val heatindexF: Double,
    @SerialName("dewpoint_c") val dewpointC: Double,
    @SerialName("dewpoint_f") val dewpointF: Double,
    @SerialName("vis_km") val visKm: Double,
    @SerialName("vis_miles") val visMiles: Double,
    val uv: Double,
    @SerialName("gust_mph") val gustMph: Double,
    @SerialName("gust_kph") val gustKph: Double
)

@Serializable
data class Condition(
    val text: String,
    val icon: String,
    val code: Int
)

fun WeatherResponse.toCity(): City {
    return City(
        name = this.location.region,
        temperature = this.current.tempF,
        temperatureFeelsLike = this.current.feelslikeF,
        humidity = this.current.humidity,
        uvIndex = this.current.uv,
        condition = City.Condition(
            description = this.current.condition.text,
            iconURL = this.current.condition.icon
        )
    )
}
