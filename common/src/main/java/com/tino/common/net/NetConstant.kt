package com.tino.common.net

/**
 * Retrofit、Okhttp相关配置信息
 */
object NetConstant {
    /**
     * baseUrl
     */
    const val BASEURL_DEFAULT = "https://weatherquery.api.bdymkt.com/"
    const val BASEURL = "https://weatherquery.api.bdymkt.com/"
    const val BASEURL2 = "https://www.douban.com/"
    const val BASEURL3 = "http://douban.fm/"
    const val BASEURL4 = "https://api.vvhan.com/"
    const val BASEURL5 = "http://tucdn.wpon.cn/"

    /**
     * 对应host
     */
    const val HOST1_DEFAULT = "DEFAULT"
    const val HOST1 = "Baidu"
    const val HOST2 = "Douban"
    const val HOST3 = "DoubanFm"
    const val HOST4 = "RandomMusic"
    const val HOST5 = "Video"


    /**
     * Okhttp cacheSize
     */
    const val CAGESIZE = 100 * 1024 * 1024

    /**
     * Okhttp timeout
     */
    const val DEFAULT_CONNECT_TIME = 8
    const val DEFAULT_WRITE_TIME = 30
    const val DEFAULT_READ_TIME = 30
}