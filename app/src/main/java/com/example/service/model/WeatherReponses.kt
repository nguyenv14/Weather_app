import java.io.Serializable

data class Weather(
    val location: Location,
    val current: Current,
    val forecast: Forecast
): Serializable

data class Forecast (
    val forecastday: List<Forecastday>
): Serializable

data class Forecastday (
    val date: String,
    val date_epoch: Long,
    val day: Day,
    val astro: Astro,
    val hour: List<Hour>
): Serializable

data class Astro (
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
    val moon_phase: String,
    val moon_illumination: String,
    val is_moon_up: Long,
    val is_sun_up: Long
): Serializable

data class Day (
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val mintemp_c: Double,
    val mintemp_f: Double,
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val maxwind_mph: Double,
    val maxwind_kph: Double,
    val totalprecip_mm: Double,
    val totalprecip_in: Double,
    val totalsnow_cm: Double,
    val avgvis_km: Double,
    val avgvis_miles: Double,
    val avghumidity: Double,
    val daily_will_it_rain: Long,
    val daily_chance_of_rain: Long,
    val daily_will_it_snow: Long,
    val daily_chance_of_snow: Long,
    val condition: Condition,
    val uv: Double,
    val air_quality: AirQuality
): Serializable

data class Hour (
    val time_epoch: Long,
    val time: String,
    val temp_c: Double,
    val temp_f: Double,
    val is_day: Long,
    val condition: Condition,
    val wind_mph: Double,
    val wind_kph: Double,
    val wind_degree: Long,
    val wind_dir: String,
    val pressure_mB: Double,
    val pressure_in: Double,
    val precip_mm: Double,
    val precip_in: Double,
    val humidity: Long,
    val cloud: Long,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val windchill_c: Double,
    val windchill_f: Double,
    val heatindex_c: Double,
    val heatindex_f: Double,
    val dewpoint_c: Double,
    val dewpoint_f: Double,
    val will_it_rain: Long,
    val chance_of_rain: Long,
    val will_it_snow: Long,
    val chance_of_snow: Long,
    val vis_km: Double,
    val vis_miles: Double,
    val gust_mph: Double,
    val gust_kph: Double,
    val uv: Double
): Serializable

