package shahzod.projects.data.mappers

import shahzod.projects.data.remote.GeocodingResultDto
import shahzod.projects.data.remote.NearbyWeatherDto
import shahzod.projects.data.remote.WeatherDto
import shahzod.projects.domain.location.City
import shahzod.projects.domain.weather.DailyWeather
import shahzod.projects.domain.weather.NearbyLocation
import shahzod.projects.domain.weather.WeatherData
import shahzod.projects.domain.weather.WeatherInfo
import shahzod.projects.domain.weather.WeatherType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

private fun <T> List<T?>?.at(index: Int): T? = this?.getOrNull(index)

/** "2026-10-05T06:12" -> LocalDateTime. Bo'sh yoki noto'g'ri qiymat (qutb kunlari) uchun null. */
private fun String?.toLocalDateTimeOrNull(): LocalDateTime? =
    this?.takeIf { it.isNotBlank() }?.let { runCatching { LocalDateTime.parse(it) }.getOrNull() }

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val h = requireNotNull(hourly) { "Hourly data is missing" }

    val hourlyList = h.time.orEmpty().mapIndexed { i, timeString ->
        WeatherData(
            time = LocalDateTime.parse(timeString),
            temperatureCelsius = h.temperature.at(i) ?: 0.0,
            pressure = h.pressures.at(i) ?: 0.0,
            windSpeed = h.windSpeeds.at(i) ?: 0.0,
            humidity = h.humidities.at(i) ?: 0.0,
            precipitationProbability = h.precipitationProbabilities.at(i) ?: 0,
            weatherType = WeatherType.fromWMO(h.weatherCodes.at(i) ?: 0)
        )
    }

    val dailyList = daily?.let { d ->
        d.time.orEmpty().mapIndexed { i, dateString ->
            DailyWeather(
                date = LocalDate.parse(dateString),
                maxTemperature = d.maxTemperatures.at(i) ?: 0.0,
                minTemperature = d.minTemperatures.at(i) ?: 0.0,
                precipitationProbabilityMax = d.precipitationProbabilityMax.at(i) ?: 0,
                windSpeedMax = d.windSpeedMax.at(i) ?: 0.0,
                weatherType = WeatherType.fromWMO(d.weatherCodes.at(i) ?: 0),
                sunrise = d.sunrises.at(i).toLocalDateTimeOrNull(),
                sunset = d.sunsets.at(i).toLocalDateTimeOrNull()
            )
        }
    }.orEmpty()

    val now = LocalDateTime.now(ZoneOffset.ofTotalSeconds(utcOffsetSeconds ?: 0))

    return WeatherInfo(hourly = hourlyList, daily = dailyList, now = now)
}

fun GeocodingResultDto.toCity(): City? {
    val cityName = name ?: return null
    val lat = latitude ?: return null
    val lon = longitude ?: return null
    return City(
        name = cityName,
        country = country,
        region = admin1,
        latitude = lat,
        longitude = lon
    )
}
fun NearbyWeatherDto.toNearbyLocation(): NearbyLocation {
    val currentData = current
    val lat = latitude ?: 0.0
    val lon = longitude ?: 0.0

    return NearbyLocation(
        name = "Location ($lat, $lon)",
        latitude = lat,
        longitude = lon,
        temperatureCelsius = currentData?.temperature ?: 0.0,
        windSpeed = currentData?.windSpeed ?: 0.0,
        weatherType = WeatherType.fromWMO(currentData?.weatherCode ?: 0),
        isDay = (currentData?.isDay ?: 1) == 1
    )
}

fun List<NearbyWeatherDto>.toNearbyLocationList(): List<NearbyLocation> {
    return map { it.toNearbyLocation() }
}