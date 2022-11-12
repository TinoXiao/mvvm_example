package com.tino.weather.net

import com.tino.weather.bean.BDWeather
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface BDWeatherService {
    @Headers("X-Bce-Signature: AppCode/ce5cffeecbc04779b227814847629a34")
    @POST("weather/query/by-area")
    @FormUrlEncoded
    fun postWeatherBD(@Field("area") city: String): Call<BDWeather>

    @Headers("X-Bce-Signature: AppCode/ce5cffeecbc04779b227814847629a34")
    @POST("weather/query/by-area")
    @FormUrlEncoded
    fun postWeatherBD2(@Field("area") city: String): Single<BDWeather>

    @Headers("X-Bce-Signature: AppCode/ce5cffeecbc04779b227814847629a34")
    @POST("weather/query/by-area")
    @FormUrlEncoded
    suspend fun getCoroutinWeather(@Field("area") city: String): BDWeather

    @Headers("X-Bce-Signature: AppCode/ce5cffeecbc04779b227814847629a34")
    @POST("weather/query/by-area")
    @FormUrlEncoded
    fun getCoroutinWeather2(@Field("area") city: String): Call<BDWeather>

}