package com.tino.music.net

import com.tino.music.bean.MusicData
import retrofit2.http.*

interface MusicService {


    @GET("api/rand.music")
    suspend fun getRandomMusic(@Query("sort") sort :String,
                               @Query("type") type :String = "json") : MusicData
}