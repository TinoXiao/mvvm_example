package com.tino.common.db.dao

import androidx.room.*
import com.tino.common.db.bean.Future
import io.reactivex.Completable

@Dao
interface FutureDao {
    @Delete
    fun delete(future: Future)

    @Insert
    fun insertWeather(future: Future)

    @Insert
    fun insertWeathers(weathers: List<Future>?): Completable

    @Update
    fun updateWeather(future: Future?)

    @Query("select * from future where wId=:id")
    fun getAllWeather(id: Int): List<Future>

    @Query("select * from future where wid= :id order By future.daytime asc")
    suspend fun getFutureById(id: Long): List<Future>

    @Insert
    suspend fun insertWeathersCoroutine(weathers: List<Future>)
}