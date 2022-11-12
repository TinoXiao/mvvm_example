package com.tino.weather

import com.tino.base.application.BaseApplication
import com.tino.common.db.AppDataBase
import com.tino.common.db.dao.FutureDao
import com.tino.common.db.dao.WeatherDao

class WeatherApplication : BaseApplication() {
    companion object{
        lateinit var weatherDao : WeatherDao
        lateinit var futureDao: FutureDao
    }

    override fun onCreate() {
        super.onCreate()
        weatherDao = AppDataBase.getInstance(mContext).getWeatherDao()
        futureDao = AppDataBase.getInstance(mContext).getFutureDao()
    }
}