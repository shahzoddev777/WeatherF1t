package shahzod.projects.domain.repository

import kotlinx.coroutines.flow.Flow
import shahzod.projects.domain.util.Resource
import shahzod.projects.domain.weather.NearbyLocation
import shahzod.projects.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherInfo(lat: Double, long: Double): Flow<Resource<WeatherInfo>>

    suspend fun getNearbyWeather(lat: Double, long: Double): Flow<Resource<List<NearbyLocation>>>
}
