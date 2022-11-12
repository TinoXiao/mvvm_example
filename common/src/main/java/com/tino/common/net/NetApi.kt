package com.tino.common.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import okhttp3.logging.HttpLoggingInterceptor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.tino.common.CommonConstant
import com.tino.kotlinmvvm.net.interceptor.ResponseInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.HashMap
import java.util.concurrent.TimeUnit

/**
 * 网络相关封装基类
 * 对Okhttp、Retrofit进行再封装
 */
object NetApi {
    private lateinit var baseUrl: String
    private lateinit var okHttpClient: OkHttpClient

    init{
        val builder = OkHttpClient.Builder()
        //添加请求拦截器，如果接口有请求头的话，可以放在这个拦截器里面
//        builder.addInterceptor(RequestInterceptor())
        //添加返回拦截器，可用于查看接口的请求耗时，对于网络优化有帮助
        builder.addInterceptor(ResponseInterceptor())
        builder.connectTimeout(NetConstant.DEFAULT_CONNECT_TIME.toLong(), TimeUnit.SECONDS)
        builder.writeTimeout(NetConstant.DEFAULT_WRITE_TIME.toLong(), TimeUnit.SECONDS)
        builder.readTimeout(NetConstant.DEFAULT_READ_TIME.toLong(), TimeUnit.SECONDS)
        //忽略https证书
        builder.sslSocketFactory(NetSet.getSSLSocketFactory(),NetSet.getTrustManager())
        builder.hostnameVerifier(NetSet.getHostnameVerifier())
        //当程序在debug过程中则打印数据日志，方便调试用。
        if (CommonConstant.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            //设置要打印日志的内容等级，BODY为主要内容，还有BASIC、HEADERS、NONE。
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            //将拦截器添加到OkHttp构建器中
            builder.addNetworkInterceptor(httpLoggingInterceptor)
        }
        okHttpClient = builder.build()
    }

    public fun getClient(): OkHttpClient{
        return okHttpClient
    }

    /**
     * 用于存放不同host的Retrofit实例，避免重复创建的资源消耗
     */
    private val retrofitHashMap = HashMap<String, Retrofit?>()

    /**
     * 创建serviceClass的实例
     * 默认host
     */
    fun <T> createService(serviceClass: Class<T>): T {
        //设置Url类型
        setUrlType(NetConstant.HOST1_DEFAULT)
        return getRetrofit(NetConstant.HOST1_DEFAULT)!!.create(serviceClass)
    }

    /**
     * 创建serviceClass的实例
     * 如果接口有不同的host，可以调用此方法设置baseUrl
     */
    fun <T> createService(serviceClass: Class<T>, type: String): T {
        //设置Url类型
        setUrlType(type)
        return getRetrofit(type)!!.create(serviceClass)
    }

    /**
     * 设置访问Url类型
     *
     * @param type 0 必应 1 壁纸列表
     */
    private fun setUrlType(type: String) {
        baseUrl = when (type) {
            NetConstant.HOST1 -> NetConstant.BASEURL
            NetConstant.HOST2 -> NetConstant.BASEURL2
            NetConstant.HOST3 -> NetConstant.BASEURL3
            NetConstant.HOST4 -> NetConstant.BASEURL4
            NetConstant.HOST5 -> NetConstant.BASEURL5
            else -> NetConstant.BASEURL_DEFAULT
        }
    }

    /**
     * 配置Retrofit
     *
     * @param hostType host
     */
    private fun getRetrofit(hostType: String): Retrofit? {
        if (retrofitHashMap[hostType] != null) {
            //刚才上面定义的Map中键是String，值是Retrofit，当键不为空时，必然有值，有值则直接返回。
            return retrofitHashMap[hostType]
        }
        //初始化Retrofit  Retrofit是对OKHttp的封装，通常是对网络请求做处理，也可以处理返回数据。
        //Retrofit构建器
        val builder = Retrofit.Builder()
        //设置访问地址
        builder.baseUrl(baseUrl)
        //设置OkHttp客户端，传入上面写好的方法即可获得配置后的OkHttp客户端。
        builder.client(okHttpClient)
        //设置数据解析器 会自动把请求返回的结果（json字符串）通过Gson转化工厂自动转化成与其结构相符的实体Bean
        builder.addConverterFactory(GsonConverterFactory.create())
        //设置请求回调，使用RxJava 对网络返回进行处理
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        //retrofit配置完成
        val retrofit = builder.build()
        //放入Map中
        retrofitHashMap[hostType] = retrofit
        //最后返回即可
        return retrofit
    }

}