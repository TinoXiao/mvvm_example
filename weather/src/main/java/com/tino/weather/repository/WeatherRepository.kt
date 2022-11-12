package com.tino.weather.repository

import android.util.Log
import com.tino.base.util.DateUtil
import com.tino.common.db.bean.Future
import com.tino.common.db.bean.Weather
import com.tino.common.net.NetApi
import com.tino.weather.WeatherApplication
import com.tino.weather.bean.BDWeather
import com.tino.weather.bean.BdWeatherData
import com.tino.weather.bean.Days
import com.tino.weather.bean.Today
import com.tino.weather.net.BDWeatherService
import java.util.ArrayList

class WeatherRepository {

    suspend fun getWeatherFuture(id:Long): List<Future>{
        return WeatherApplication.futureDao.getFutureById(id)
    }


    fun bdWeatherToDbWeather(weather: BDWeather): Weather {
        val w = Weather()
        val data: BdWeatherData = weather.data!!
        val today: Today = data.now!!
        w.areaId=data.areaId
        w.city=data.city
        w.province=data.province
        w.sd=today.sd
        w.aqi=today.aqi
        w.weather=today.weather
        w.weather_pic=today.weather_pic
        w.temperature=today.temperature
        w.wind_direction=today.wind_direction
        w.wind_power=today.wind_power
        //保存最后请求日期
        w.last_cache= DateUtil.theYearMonthAndDay
        return w
    }

    suspend fun getLocalWeather(city:String):Weather{
        return WeatherApplication.weatherDao.getCoroutineWeather(city)
    }
    suspend fun getNetWeather(city:String):BDWeather{
        return NetApi.createService(BDWeatherService::class.java).getCoroutinWeather(city)
    }

    suspend fun saveToDbCoroutine(weather: BDWeather):Long {
        var id = -1L
        Log.e("AAA", "开始本地插入数据")
        val w: Weather = bdWeatherToDbWeather(weather)
        //删除该城市天气信息
        WeatherApplication.weatherDao.deleteCityCoroutine(w.city!!)
        //插入天气信息,并返回id
        id = WeatherApplication.weatherDao.insertWeatherCoroutine(w)
        val futures: ArrayList<Days> = weather.data!!.dayWeathers!!
        val dbFutures: MutableList<Future> = mutableListOf<Future>()
        for (i in futures.indices) {
            val future = Future()
            var date = futures[i].daytime
            date = DateUtil.change(date!!)
            if(w.last_cache.equals(date)){
                future.day_weather = w.weather
                future.day_weather_pic = w.weather_pic
                future.daytime = date
                future.day_high_temperature = w.temperature
                future.day_wind_direction = w.wind_direction
                future.day_wind_power = w.wind_power
            }else{
                future.day_weather = futures[i].day_weather
                future.day_weather_code = futures[i].day_weather_code
                future.day_weather_pic = futures[i].day_weather_pic
                future.daytime = futures[i].daytime
                future.day_high_temperature = futures[i].day_high_temperature
                future.day_wind_direction = futures[i].day_wind_direction
                future.day_wind_power = futures[i].day_wind_power
            }
            future.wid = id
            future.night_weather = futures[i].night_weather
            future.night_weather_code = futures[i].night_weather_code
            future.night_weather_pic = futures[i].night_weather_pic
            future.night_low_temperature = futures[i].night_low_temperature
            future.night_wind_direction = futures[i].night_wind_direction
            future.night_wind_power = futures[i].night_wind_power
            dbFutures.add(future)
        }
        WeatherApplication.futureDao.insertWeathersCoroutine(dbFutures)
        return id
    }
}