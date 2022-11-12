package com.tino.common.db.dao

import androidx.room.*
import com.tino.common.db.bean.Weather
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface WeatherDao {
    @Delete
    fun delete(weather: Weather)

    @Insert
    fun insertWeather(weather: Weather): Single<Long>

    @Update
    fun updateWeather(weather: Weather)

    @Query("select * from weather where city= :city")
    fun getWeatherByCityName(city: String): Single<List<Weather>>

    @Query("select * from weather where city= :city")
    suspend fun getCoroutineWeather(city: String): Weather

    @Query("delete from weather where city=:city")
    fun deleteCity(city: String): Completable

    @Query("delete from weather where city=:city")
    suspend fun deleteCityCoroutine(city: String)

    @Insert
    suspend fun insertWeatherCoroutine(weather: Weather): Long
}