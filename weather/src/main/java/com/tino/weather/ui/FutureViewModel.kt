package com.tino.weather.ui

import android.util.Log
import androidx.lifecycle.*
import com.tino.base.error.BaseError
import com.tino.base.viewmodel.BaseViewModel
import com.tino.common.db.bean.Weather
import com.tino.common.db.bean.entity.Weathers
import com.tino.weather.repository.WeatherRepository

class FutureViewModel : BaseViewModel() {

    val weathersMutableLiveData: MutableLiveData<Weathers> by lazy {
        MutableLiveData<Weathers>()
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

    fun getFuture(w: Weather) {
        updataUI(w)
        vLaunchWithLoading {
            loading.postValue(true)
            try {
                val list = weatherRepository.getWeatherFuture(w.id)
                if (list != null && list.isNotEmpty()) {
                    val weathers = weathersMutableLiveData!!.value
                    weathers!!.futureList = list
                    weathersMutableLiveData.postValue(weathers!!)
                } else {
                    throw BaseError("获取数据失败")
                }
            } catch (e: Exception) {
                throw BaseError("获取数据失败", e)
            }
        }
    }

    private fun updataUI(weather:Weather){
        var weathers = weathersMutableLiveData.value
        weathers!!.weather = weather
        weathersMutableLiveData.postValue(weathers!!)
    }

}