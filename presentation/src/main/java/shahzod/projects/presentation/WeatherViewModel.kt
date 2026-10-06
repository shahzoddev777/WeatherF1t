package shahzod.projects.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import shahzod.projects.domain.location.City
import shahzod.projects.domain.location.LocationTracker
import shahzod.projects.domain.repository.GeocodingRepository
import shahzod.projects.domain.repository.SavedLocationsRepository
import shahzod.projects.domain.repository.WeatherRepository
import shahzod.projects.domain.util.Resource
import shahzod.projects.presentation.ui.util.AppLanguage
import shahzod.projects.presentation.ui.util.LanguageManager
import shahzod.projects.presentation.ui.util.getAppStrings
import java.util.Locale
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val geocodingRepository: GeocodingRepository,
    private val savedLocationsRepository: SavedLocationsRepository,
    private val locationTracker: LocationTracker,
    private val languageManager: LanguageManager
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state.asStateFlow()

    private var loadJob: Job? = null
    private var nearbyJob: Job? = null
    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            _state.update { it.copy(savedLocations = savedLocationsRepository.getAll()) }
        }
        viewModelScope.launch {
            languageManager.currentLanguage.collect { lang ->
                _state.update {
                    it.copy(
                        currentLanguage = lang,
                        strings = getAppStrings(lang)
                    )
                }
            }
        }
    }

    fun selectLanguage(language: AppLanguage) {
        languageManager.setLanguage(language)
    }

    fun useMyLocation() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            val location = locationTracker.getCurrentLocation()
            if (location == null) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Joylashuvni aniqlab bo'lmadi. Ruxsat berilganini va GPS yoqilganini tekshiring yoki shahar qidiring."
                    )
                }
            } else {
                loadWeather(location.latitude, location.longitude, name = null)
            }
        }
    }

    fun loadForCoordinates(latitude: Double, longitude: Double, name: String? = null) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch { loadWeather(latitude, longitude, name) }
    }

    fun refresh() {
        val s = _state.value
        val lat = s.latitude
        val lon = s.longitude
        if (lat != null && lon != null) {
            loadForCoordinates(lat, lon, s.locationName.ifBlank { null })
        } else {
            useMyLocation()
        }
    }

    fun onLocationPermissionDenied() {
        _state.update {
            it.copy(
                isLoading = false,
                error = "Joylashuv ruxsati berilmadi. Shahar nomini qidirib ko'ring."
            )
        }
    }

    private suspend fun loadWeather(lat: Double, lon: Double, name: String?) {
        _state.update { it.copy(isLoading = true, error = null) }

        weatherRepository.getWeatherInfo(lat, lon).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val info = result.data
                    if (info == null) {
                        _state.update { it.copy(isLoading = false, error = "Ob-havo ma'lumotlari bo'sh keldi.") }
                        return@collect
                    }
                    val resolvedName = name
                        ?: withTimeoutOrNull(5_000) { geocodingRepository.reverseGeocode(lat, lon) }
                        ?: String.format(Locale.US, "%.2f, %.2f", lat, lon)

                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            weatherInfo = info,
                            locationName = resolvedName,
                            latitude = lat,
                            longitude = lon,
                            selectedHourIndex = 0,
                            selectedDayIndex = 0,
                            nearbyLocations = emptyList()
                        )
                    }
                    loadNearby(lat, lon, resolvedName)
                }

                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }

        _state.update {
            if (it.isLoading) it.copy(isLoading = false, error = it.error ?: "Ob-havo ma'lumotlarini yuklab bo'lmadi.") else it
        }
    }

    private fun loadNearby(lat: Double, lon: Double, currentName: String) {
        nearbyJob?.cancel()
        nearbyJob = viewModelScope.launch {
            val currentShort = currentName.substringBefore(",").trim()
            weatherRepository.getNearbyWeather(lat, lon).collect { result ->
                if (result is Resource.Success) {
                    val places = result.data.orEmpty()
                        .filter { it.name.substringBefore(",").trim() != currentShort }
                        .take(3)
                    _state.update { it.copy(nearbyLocations = places) }
                }
            }
        }
    }

    fun selectHour(index: Int) = _state.update { it.copy(selectedHourIndex = index) }

    fun selectDay(index: Int) = _state.update { it.copy(selectedDayIndex = index) }

    fun onQueryChange(query: String) {
        searchJob?.cancel()
        val searchable = query.trim().length >= 2
        _state.update {
            it.copy(
                searchQuery = query,
                searchError = null,
                isSearching = searchable,
                searchResults = if (searchable) it.searchResults else emptyList()
            )
        }
        if (!searchable) return

        searchJob = viewModelScope.launch {
            delay(400)
            when (val result = geocodingRepository.searchCities(query)) {
                is Resource.Success -> _state.update {
                    it.copy(searchResults = result.data.orEmpty(), isSearching = false)
                }

                is Resource.Error -> _state.update {
                    it.copy(searchResults = emptyList(), isSearching = false, searchError = result.message)
                }
            }
        }
    }

    fun selectCity(city: City) {
        searchJob?.cancel()
        _state.update {
            it.copy(searchQuery = "", searchResults = emptyList(), isSearching = false, searchError = null)
        }
        loadForCoordinates(city.latitude, city.longitude, city.displayName)
    }

    fun toggleSaved() {
        val s = _state.value
        val lat = s.latitude ?: return
        val lon = s.longitude ?: return
        viewModelScope.launch {
            val existing = s.savedLocations.firstOrNull {
                abs(it.latitude - lat) < 0.01 && abs(it.longitude - lon) < 0.01
            }
            if (existing != null) {
                savedLocationsRepository.remove(existing)
            } else {
                savedLocationsRepository.save(
                    City(
                        name = s.locationName.substringBefore(",").trim().ifBlank { "Saved place" },
                        country = s.locationName.substringAfter(",", "").trim().ifBlank { null },
                        region = null,
                        latitude = lat,
                        longitude = lon
                    )
                )
            }
            _state.update { it.copy(savedLocations = savedLocationsRepository.getAll()) }
        }
    }
}
