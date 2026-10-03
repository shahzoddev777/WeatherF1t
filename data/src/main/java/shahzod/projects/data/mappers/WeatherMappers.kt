package shahzod.projects.data.mappers

import shahzod.projects.data.remote.WeatherDataDto
import shahzod.projects.data.remote.WeatherDto
import shahzod.projects.domain.weather.WeatherData
import shahzod.projects.domain.weather.WeatherInfo
import shahzod.projects.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time?.mapIndexed { index, timeString ->
        val temperature = temperature?.get(index) ?: 0.0
        val weatherCode = weatherCodes?.get(index) ?: 0
        val windSpeed = windSpeeds?.get(index) ?: 0.0
        val pressure = pressures?.get(index) ?: 0.0
        val humidity = humidities?.get(index) ?: 0.0

        WeatherData(
            time = LocalDateTime.parse(timeString, DateTimeFormatter.ISO_DATE_TIME),
            temperatureCelsius = temperature,
            pressure = pressure,
            windSpeed = windSpeed,
            humidity = humidity,
            weatherType = WeatherType.fromWMO(weatherCode)
        )
    }?.groupBy {
        it.time.dayOfMonth
    } ?: emptyMap()
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()

    val currentWeatherData = weatherDataMap[now.dayOfMonth]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }

    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}