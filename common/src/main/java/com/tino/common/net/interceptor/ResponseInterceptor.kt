package com.tino.kotlinmvvm.net.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import okio.GzipSource
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import kotlin.Throws

/**
 * 返回拦截器(响应拦截器)
 *
 */
class ResponseInterceptor : Interceptor {
    /**
     * 拦截
     */
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())
        val responseBody = response.body
        if(responseBody!=null){

            /**
             * 拦截某些奇怪的返回数据格式
             */
            var json = responseBody.string()
            if(json.contains("</html>")){
                json = json.substringAfterLast("</html>",json)
            }
            val boy = json.toResponseBody(responseBody.contentType())
            return response.newBuilder().body(boy).build()
        }
        return response
    }
    
}