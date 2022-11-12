package com.tino.common.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.Throws

/**
 * 请求拦截器
 */
class RequestInterceptor : Interceptor {
    /**
     * 拦截
     */
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // TODO: 2022/8/5
        // 打印request,添加信心等操作
        return chain.proceed(chain.request())
    }
}