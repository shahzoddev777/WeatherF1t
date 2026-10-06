package shahzod.projects.data.remote

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("utc_offset_seconds") val utcOffsetSeconds: Int?,
    @SerializedName("hourly") val hourly: HourlyDto?,
    @SerializedName("daily") val daily: DailyDto?
)

data class HourlyDto(
    @SerializedName("time") val time: List<String>?,
    @SerializedName("temperature_2m") val temperature: List<Double?>?,
    @SerializedName("weather_code") val weatherCodes: List<Int?>?,
    @SerializedName("relative_humidity_2m") val humidities: List<Double?>?,
    @SerializedName("wind_speed_10m") val windSpeeds: List<Double?>?,
    @SerializedName("pressure_msl") val pressures: List<Double?>?,
    @SerializedName("precipitation_probability") val precipitationProbabilities: List<Int?>?
)

data class DailyDto(
    @SerializedName("time") val time: List<String>?,
    @SerializedName("weather_code") val weatherCodes: List<Int?>?,
    @SerializedName("temperature_2m_max") val maxTemperatures: List<Double?>?,
    @SerializedName("temperature_2m_min") val minTemperatures: List<Double?>?,
    @SerializedName("precipitation_probability_max") val precipitationProbabilityMax: List<Int?>?,
    @SerializedName("wind_speed_10m_max") val windSpeedMax: List<Double?>?,
    @SerializedName("sunrise") val sunrises: List<String?>?,
    @SerializedName("sunset") val sunsets: List<String?>?
)

data class NearbyWeatherDto(
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?,
    @SerializedName("current") val current: CurrentDto?
)

data class CurrentDto(
    @SerializedName("temperature_2m") val temperature: Double?,
    @SerializedName("weather_code") val weatherCode: Int?,
    @SerializedName("wind_speed_10m") val windSpeed: Double?,
    @SerializedName("is_day") val isDay: Int?
)
