package shahzod.projects.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponseDto(
    @Json(name = "hourly")
    val hourly: WeatherDataDto?
)

@JsonClass(generateAdapter = true)
data class WeatherDataDto(
    val time: List<String>?,

    @Json(name = "temperature_2m")
    val temperature: List<Double>?,

    @Json(name = "weathercode")
    val weatherCodes: List<Int>?,

    @Json(name = "pressure_msl")
    val pressures: List<Double>?,

    @Json(name = "windspeed_10m")
    val windSpeeds: List<Double>?,

    @Json(name = "relativehumidity_2m")
    val humidities: List<Double>?
)