package com.tino.weather.bean

import java.util.ArrayList

data class BDWeather (
    var code: Int = 0,
    var msg: String? = null,
    var taskNo: String? = null,
    var data: BdWeatherData? = null,
)

data class BdWeatherData(
    var area: String?,
    var areaId: String?,
    var province: String?,
    var city: String?,
    var now: Today?,
    var dayWeathers: ArrayList<Days>?
)

data class Today (
    var sd: String? = null,
    var aqi: String? = null,
    var weather: String? = null,
    var weather_pic: String? = null,
    var temperature: String? = null,
    var wind_direction: String? = null,
    var wind_power: String? = null
)

data class Days (
    var daytime: String? = null,
    var day_weather: String? = null,
    var day_weather_code: String? = null,
    var day_weather_pic: String? = null,
    var day_high_temperature: String? = null,
    var day_wind_direction: String? = null,
    var day_wind_power: String? = null,
    var night_weather: String? = null,
    var night_weather_code: String? = null,
    var night_weather_pic: String? = null,
    var night_low_temperature: String? = null,
    var night_wind_direction: String? = null,
    var night_wind_power: String? = null
)
