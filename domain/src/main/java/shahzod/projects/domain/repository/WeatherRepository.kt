package shahzod.projects.domain.repository

import kotlinx.coroutines.flow.Flow
import shahzod.projects.domain.util.Resource
import shahzod.projects.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Flow<Resource<WeatherInfo>>

}