package com.tino.weather.ui

import android.util.Log
import androidx.lifecycle.*
import com.tino.base.error.BaseError
import com.tino.base.util.DateUtil
import com.tino.base.viewmodel.BaseViewModel
import com.tino.common.db.bean.Weather
import com.tino.common.db.bean.entity.Weathers
import com.tino.weather.repository.WeatherRepository


class WeatherViewModel(city:String) : BaseViewModel() {

    val weathersMutableLiveData: MutableLiveData<Weathers> by lazy {
        MutableLiveData<Weathers>().also {
            getWeatherCoroutin(city)
        }
    }

    private val weatherRepository: WeatherRepository by lazy{
        WeatherRepository()
    }

    fun getWeather():LiveData<Weathers>{
        return weathersMutableLiveData
    }

    init {
        weathersMutableLiveData.value=Weathers()
    }

    /**
     * 加载天气 先本地，后网络
     */
    private fun getWeatherCoroutin(city:String){
        Log.e("AAA","重新获取天气。。。。。。。。。。。。。。。。。。")
        vLaunchWithLoading{
            loading.postValue(true)
            val weather = weatherRepository.getLocalWeather(city)
            if (weather != null) {
                Log.e("AAA","本地有数据")
                if (weather.last_cache.equals(DateUtil.theYearMonthAndDay)) {
                    updataUI(weather)
                } else {
                    Log.e("AAA","数据超时")
                    //数据超过一天，重新获取网络数据
                    getNetWeather(city)
                }
            } else {
                Log.e("AAA","无数据网络获取")
                getNetWeather(city)
            }
        }
    }

    /**
     * 网络获取最新天气
     */
    fun getNewestWeather(city:String){
        vLaunch ({
            loading.postValue(true)
            getNetWeather(city)
        },{
            loading.postValue(false)
        })
    }

    private suspend fun getNetWeather(city:String){
        var bdWeather = weatherRepository.getNetWeather(city)
        if (bdWeather == null || bdWeather.code != 200) {
            throw BaseError("网络获取数据为空")
        } else {
            val weather = weatherRepository.bdWeatherToDbWeather(bdWeather)
            //更新UI
            updataUI(weather)
            try {
                var id = weatherRepository.saveToDbCoroutine(bdWeather)
                weather.id = id
                updataUI(weather)
            }catch (e:Exception){
                throw BaseError("保存数据失败",e)
            }
        }
    }

    fun updataUI(weather: Weather){
        var weathers = weathersMutableLiveData.value
        weathers!!.weather = weather
        weathersMutableLiveData.postValue(weathers!!)
    }

}