package shahzod.projects.presentation.ui.util

import android.app.Application
import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

enum class AppLanguage(val code: String, val displayName: String) {
    UZBEK("uz", "O'zbekcha"),
    ENGLISH("en", "English"),
    RUSSIAN("ru", "Русский")
}

data class AppStrings(
    val homeTab: String,
    val forecastTab: String,
    val mapTab: String,
    val settingsTab: String,

    val settingsTitle: String,
    val languageSectionTitle: String,
    val languageSectionDescription: String,
    val locationSectionTitle: String,
    val locationSectionDescription: String,
    val useMyLocationButton: String,
    val dataSectionTitle: String,
    val dataSectionDescription: String,
    val refreshButton: String,

    val forecastReportTitle: String,
    val forecastReportSubtitle: String,
    val nearbyLocationsTitle: String,
    val savedPlacesTitle: String,
    val searchCityPlaceholder: String,
    val searchCityHint: String,
    val nothingFound: String,
    val retry: String,
    val searchCityBtn: String,

    val windSpeed: String,
    val chanceOfRain: String,
    val humidity: String,
    val pressure: String,
    val today: String,
    val tomorrow: String,
    val day: String,
    val month: String,
    val sunrise: String,
    val sunset: String,
    val runningTitle: String,
    val runConditions: String,
    val bestTimeToRun: String,
    val tempLabel: String,
    val windLabel: String,
    val rainLabel: String,
    val lightLabel: String,
    val night: String,
    val dayTime: String,

    val levelExcellent: String,
    val levelGood: String,
    val levelFair: String,
    val levelPoor: String,
    val levelBad: String
)

fun getAppStrings(language: AppLanguage): AppStrings {
    return when (language) {
        AppLanguage.UZBEK -> AppStrings(
            homeTab = "Asosiy",
            forecastTab = "Prognoz",
            mapTab = "Xarita",
            settingsTab = "Sozlamalar",
            settingsTitle = "Sozlamalar",
            languageSectionTitle = "Ilova tili",
            languageSectionDescription = "Ilova interfeysi tilini tanlang.",
            locationSectionTitle = "Joylashuv",
            locationSectionDescription = "GPS orqali joriy joylashuv ob-havosini yuklash.",
            useMyLocationButton = "Mening joylashuvim",
            dataSectionTitle = "Ma'lumotlar",
            dataSectionDescription = "Manba: Open-Meteo. Ma'lumotlar 16 kungacha prognozni o'z ichiga oladi.",
            refreshButton = "Yangilash",
            forecastReportTitle = "Ob-havo hisoboti",
            forecastReportSubtitle = "Aniq ob-havo prognozi",
            nearbyLocationsTitle = "Yaqin atrofdagi joylar",
            savedPlacesTitle = "Saqlangan joylar",
            searchCityPlaceholder = "Shahar qidirish...",
            searchCityHint = "Shahar nomini kiriting, masalan: Toshkent",
            nothingFound = "Hech narsa topilmadi",
            retry = "Qayta urinish",
            searchCityBtn = "Shahar qidirish",
            windSpeed = "Shamol tezligi",
            chanceOfRain = "Yog'ingarchilik",
            humidity = "Namlik",
            pressure = "Bosim",
            today = "Bugun",
            tomorrow = "Ertaga",
            day = "Kun",
            month = "Oylar",
            sunrise = "Quyosh chiqishi",
            sunset = "Quyosh botishi",
            runningTitle = "Yugurish",
            runConditions = "Yugurish sharoiti",
            bestTimeToRun = "Yugurish uchun eng yaxshi vaqt",
            tempLabel = "Harorat",
            windLabel = "Shamol",
            rainLabel = "Yog'in",
            lightLabel = "Yorug'lik",
            night = "Tun",
            dayTime = "Kun",
            levelExcellent = "A'lo",
            levelGood = "Yaxshi",
            levelFair = "O'rtacha",
            levelPoor = "Yomon",
            levelBad = "Tavsiya etilmaydi"
        )
        AppLanguage.ENGLISH -> AppStrings(
            homeTab = "Home",
            forecastTab = "Forecast",
            mapTab = "Map",
            settingsTab = "Settings",
            settingsTitle = "Settings",
            languageSectionTitle = "Language",
            languageSectionDescription = "Select app interface language.",
            locationSectionTitle = "Location",
            locationSectionDescription = "Load weather for current location via GPS.",
            useMyLocationButton = "Use my location",
            dataSectionTitle = "Data",
            dataSectionDescription = "Source: Open-Meteo. Data includes up to 16-day forecast.",
            refreshButton = "Refresh",
            forecastReportTitle = "Forecast Report",
            forecastReportSubtitle = "Accurate Weather Forecast",
            nearbyLocationsTitle = "Nearby Locations",
            savedPlacesTitle = "Saved places",
            searchCityPlaceholder = "Search city...",
            searchCityHint = "Enter city name, e.g. Tashkent",
            nothingFound = "Nothing found",
            retry = "Retry",
            searchCityBtn = "Search city",
            windSpeed = "Wind Speed",
            chanceOfRain = "Chance Of Rain",
            humidity = "Humidity",
            pressure = "Pressure",
            today = "Today",
            tomorrow = "Tomorrow",
            day = "Day",
            month = "Month",
            sunrise = "Sunrise",
            sunset = "Sunset",
            runningTitle = "Running",
            runConditions = "Run conditions",
            bestTimeToRun = "Best time to run",
            tempLabel = "Temp",
            windLabel = "Wind",
            rainLabel = "Rain",
            lightLabel = "Light",
            night = "Night",
            dayTime = "Day",
            levelExcellent = "Excellent",
            levelGood = "Good",
            levelFair = "Fair",
            levelPoor = "Poor",
            levelBad = "Not recommended"
        )
        AppLanguage.RUSSIAN -> AppStrings(
            homeTab = "Главная",
            forecastTab = "Прогноз",
            mapTab = "Карта",
            settingsTab = "Настройки",
            settingsTitle = "Настройки",
            languageSectionTitle = "Язык приложения",
            languageSectionDescription = "Выберите язык интерфейса приложения.",
            locationSectionTitle = "Местоположение",
            locationSectionDescription = "Загрузка погоды для текущего местоположения по GPS.",
            useMyLocationButton = "Моё местоположение",
            dataSectionTitle = "Данные",
            dataSectionDescription = "Источник: Open-Meteo. Данные включают прогноз до 16 дней.",
            refreshButton = "Обновить",
            forecastReportTitle = "Прогноз погоды",
            forecastReportSubtitle = "Точный прогноз погоды",
            nearbyLocationsTitle = "Ближайшие места",
            savedPlacesTitle = "Сохраненные места",
            searchCityPlaceholder = "Поиск города...",
            searchCityHint = "Введите название города, например: Ташкент",
            nothingFound = "Ничего не найдено",
            retry = "Повторить",
            searchCityBtn = "Искать город",
            windSpeed = "Скорость ветра",
            chanceOfRain = "Вероятность осадков",
            humidity = "Влажность",
            pressure = "Давление",
            today = "Сегодня",
            tomorrow = "Завтра",
            day = "День",
            month = "Месяцы",
            sunrise = "Восход",
            sunset = "Закат",
            runningTitle = "Бег",
            runConditions = "Условия для бега",
            bestTimeToRun = "Лучшее время для бега",
            tempLabel = "Темп",
            windLabel = "Ветер",
            rainLabel = "Дождь",
            lightLabel = "Свет",
            night = "Ночь",
            dayTime = "День",
            levelExcellent = "Отлично",
            levelGood = "Хорошо",
            levelFair = "Средне",
            levelPoor = "Плохо",
            levelBad = "Не рекомендуется"
        )
    }
}

@Singleton
class LanguageManager @Inject constructor(
    private val app: Application
) {
    private val prefs = app.getSharedPreferences("weatherf1t_prefs", Context.MODE_PRIVATE)

    private val _currentLanguage = MutableStateFlow(getSavedLanguage())
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    fun setLanguage(language: AppLanguage) {
        prefs.edit().putString("app_language", language.code).apply()
        _currentLanguage.value = language
    }

    private fun getSavedLanguage(): AppLanguage {
        val savedCode = prefs.getString("app_language", AppLanguage.UZBEK.code)
        return AppLanguage.entries.firstOrNull { it.code == savedCode } ?: AppLanguage.UZBEK
    }
}
