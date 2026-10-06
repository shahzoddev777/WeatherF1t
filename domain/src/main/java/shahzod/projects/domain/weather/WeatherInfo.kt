package shahzod.projects.domain.weather

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * Bir joy uchun to'liq prognoz.
 *
 * @param now joyning o'z vaqt zonasidagi "hozir" (Open-Meteo `utc_offset_seconds` bo'yicha hisoblanadi),
 *            shuning uchun boshqa shahar tanlanganda ham joriy soat to'g'ri aniqlanadi.
 */
data class WeatherInfo(
    val hourly: List<WeatherData>,
    val daily: List<DailyWeather>,
    val now: LocalDateTime
) {
    val today: LocalDate get() = now.toLocalDate()

    /** Eng yaqin butun soat (14:20 -> 14:00, 14:40 -> 15:00). */
    private val nearestHour: LocalDateTime =
        now.truncatedTo(ChronoUnit.HOURS).let { if (now.minute >= 30) it.plusHours(1) else it }

    val currentWeatherData: WeatherData? =
        hourly.firstOrNull { it.time == nearestHour } ?: hourly.firstOrNull()

    /** Hozirgi soatdan boshlab keyingi 24 soat. */
    val upcomingHours: List<WeatherData> =
        hourly.dropWhile { it.time.isBefore(nearestHour) }.take(24)

    private val dailyByDate: Map<LocalDate, DailyWeather> = daily.associateBy { it.date }

    /**
     * [time] shu joyning quyosh botishidan keyin yoki chiqishidan oldin bo'lsa true (tun).
     * Quyosh vaqtlari kelmasa (masalan qutb hududlari) taxminiy 06:00-19:00 oralig'i kunduz hisoblanadi.
     */
    fun isNight(time: LocalDateTime): Boolean {
        val day = dailyByDate[time.toLocalDate()]
        val sunrise = day?.sunrise
        val sunset = day?.sunset
        return if (sunrise != null && sunset != null) {
            time.isBefore(sunrise) || !time.isBefore(sunset)
        } else {
            time.hour < 6 || time.hour >= 19
        }
    }

    /**
     * Keyingi 24 soat ichidagi eng yaxshi 2 soatlik yugurish oralig'i.
     * Tungi uyqu soatlari (05:00 dan oldin va 21:00 dan keyin boshlanadiganlar) tavsiya qilinmaydi.
     */
    fun bestRunningWindow(): RunningWindow? {
        val scored = upcomingHours.map { it to it.runningConditions(isNight(it.time)).score }
        var best: RunningWindow? = null
        for (i in 0 until scored.size - 1) {
            val (first, firstScore) = scored[i]
            val (second, secondScore) = scored[i + 1]
            if (first.time.hour < 5 || first.time.hour > 21) continue
            val average = (firstScore + secondScore) / 2
            if (best == null || average > best.score) {
                best = RunningWindow(start = first.time, end = second.time.plusHours(1), score = average)
            }
        }
        return best
    }

    fun hoursOf(date: LocalDate): List<WeatherData> =
        hourly.filter { it.time.toLocalDate() == date }
}
