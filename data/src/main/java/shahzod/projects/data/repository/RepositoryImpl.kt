package shahzod.projects.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import shahzod.projects.data.mappers.toNearbyLocationList
import shahzod.projects.data.mappers.toWeatherInfo
import shahzod.projects.data.remote.WeatherApi
import shahzod.projects.domain.repository.WeatherRepository
import shahzod.projects.domain.util.Resource
import shahzod.projects.domain.weather.NearbyLocation
import shahzod.projects.domain.weather.WeatherInfo
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherInfo(
        lat: Double,
        long: Double
    ): Flow<Resource<WeatherInfo>> = flow {
        try {
            val remoteData = api.getForecast(
                latitude = lat,
                longitude = long
            )
            val weatherInfo = remoteData.toWeatherInfo()
            emit(Resource.Success(weatherInfo))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "Internet bilan aloqa yo'q. Ulanishni tekshiring."))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "Noma'lum xatolik yuz berdi."))
        }
    }

    override suspend fun getNearbyWeather(
        lat: Double,
        long: Double
    ): Flow<Resource<List<NearbyLocation>>> = flow {
        try {
            val lats = "$lat,${lat + 0.1},${lat - 0.1}"
            val longs = "$long,${long + 0.1},${long - 0.1}"

            val remoteDataList = api.getCurrentForPoints(
                latitudes = lats,
                longitudes = longs
            )

            val nearbyLocations = remoteDataList.toNearbyLocationList()
            emit(Resource.Success(nearbyLocations))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "Internet bilan aloqa yo'q. Ulanishni tekshiring."))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "Noma'lum xatolik yuz berdi."))
        }
    }
}