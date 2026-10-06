package shahzod.projects.presentation

import shahzod.projects.domain.location.City
import shahzod.projects.domain.weather.NearbyLocation
import shahzod.projects.domain.weather.WeatherData
import shahzod.projects.domain.weather.WeatherInfo
import kotlin.math.abs

data class WeatherState(
    val isLoading: Boolean = false,
    val weatherInfo: WeatherInfo? = null,
    val error: String? = null,

    val locationName: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,

    val selectedHourIndex: Int = 0,
    val selectedDayIndex: Int = 0,
    val nearbyLocations: List<NearbyLocation> = emptyList(),

    val searchQuery: String = "",
    val searchResults: List<City> = emptyList(),
    val isSearching: Boolean = false,
    val searchError: String? = null,
    val savedLocations: List<City> = emptyList(),

    val currentLanguage: shahzod.projects.presentation.ui.util.AppLanguage = shahzod.projects.presentation.ui.util.AppLanguage.UZBEK,
    val strings: shahzod.projects.presentation.ui.util.AppStrings = shahzod.projects.presentation.ui.util.getAppStrings(shahzod.projects.presentation.ui.util.AppLanguage.UZBEK)
) {
    val displayedWeather: WeatherData?
        get() = weatherInfo?.let { it.upcomingHours.getOrNull(selectedHourIndex) ?: it.currentWeatherData }

    val isCurrentLocationSaved: Boolean
        get() {
            val lat = latitude ?: return false
            val lon = longitude ?: return false
            return savedLocations.any { abs(it.latitude - lat) < 0.01 && abs(it.longitude - lon) < 0.01 }
        }
}
