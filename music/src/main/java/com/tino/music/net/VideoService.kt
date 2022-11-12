package com.tino.music.net


import com.tino.music.bean.Video
import retrofit2.http.*

interface VideoService {

    @GET("api-girl/index.php?wpon=json")
    suspend fun getVideo() : Video
}