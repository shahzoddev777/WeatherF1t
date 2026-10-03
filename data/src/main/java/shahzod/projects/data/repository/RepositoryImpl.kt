package shahzod.projects.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import shahzod.projects.data.mappers.toWeatherInfo
import shahzod.projects.data.remote.WeatherApi
import shahzod.projects.domain.repository.WeatherRepository
import shahzod.projects.domain.util.Resource
import shahzod.projects.domain.weather.WeatherInfo
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherData(
        lat: Double,
        long: Double
    ): Flow<Resource<WeatherInfo>> = flow {
        runCatching {
            api.getWeatherData(
                lat = lat,
                long = long
            )
        }.onSuccess {
            emit(Resource.Success(it.toWeatherInfo()))
        }
    }
}