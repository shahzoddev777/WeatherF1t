package shahzod.projects.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String = HOURLY_VARIABLES,
        @Query("daily") daily: String = DAILY_VARIABLES,
        @Query("timezone") timezone: String = "auto",
        @Query("forecast_days") forecastDays: Int = 16
    ): WeatherDto


    @GET("v1/forecast")
    suspend fun getCurrentForPoints(
        @Query("latitude", encoded = true) latitudes: String,
        @Query("longitude", encoded = true) longitudes: String,
        @Query("current") current: String = CURRENT_VARIABLES,
        @Query("timezone") timezone: String = "auto"
    ): List<NearbyWeatherDto>

    companion object {
        const val HOURLY_VARIABLES =
            "temperature_2m,weather_code,relative_humidity_2m,wind_speed_10m,pressure_msl,precipitation_probability"
        const val DAILY_VARIABLES =
            "weather_code,temperature_2m_max,temperature_2m_min,precipitation_probability_max,wind_speed_10m_max,sunrise,sunset"
        const val CURRENT_VARIABLES = "temperature_2m,weather_code,wind_speed_10m,is_day"
    }
}
