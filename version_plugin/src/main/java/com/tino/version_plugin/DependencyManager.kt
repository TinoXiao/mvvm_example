package com.tino.version_plugin

object AndroidVersion {
    const val buildTools = "33.0.0"
    const val compileSdk = 32
    const val minSdk = 21
    const val targetSdk = 30
    const val versionCode = 1
    const val versionName = "1.0"
}

/**
 * 项目相关配置
 */
object DVersion {

    /**
     * coroutines协程
     */
    private const val coroutines_version = "1.6.4"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"

    /**
     * 腾讯mvkv
     */
    const val mmkv = "com.tencent:mmkv:1.0.23"

    /**
     * 权限申请
     */
    const val permissionx = "com.guolindev.permissionx:permissionx:1.7.1"

    /**
     * lifecycle
     */
    private const val lifecycleVersion = "2.5.0"
    const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"

    /**
     * 支持activityViewModels、fragemntModels
     */
    const val fragment_ktx = "androidx.fragment:fragment-ktx:1.5.2"

    /**
     * okhttp
     */
    private const val okhttpVersion = "4.10.0"
    const val okhttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"
    //打印信息拦截器
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
    const val okio = "com.squareup.okio:okio:1.15.0"

    /**
     * retrofit
     */
    private const val retrofitVersion = "2.9.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    //retrofit Gosn转换依赖
    const val converter_gson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    //retrofit中的RxJava2 Adapter支持
    const val retrofit2_rxjava2_adapter = "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0"

    /**
     * room
     */
    private const val roomVersion = "2.4.2"
    const val room_runtime = "androidx.room:room-runtime:$roomVersion"
    const val room_compiler = "androidx.room:room-compiler:$roomVersion"
    const val room_ktx = "androidx.room:room-ktx:$roomVersion"
    //Room 支持paging
    const val room_paging = "androidx.room:room-paging:$roomVersion"
    //Room 支持RxJava2
    const val room_rxjava2 = "androidx.room:room-rxjava2:$roomVersion"

    /**
     * navigation
     */
    private const val navigationVersion = "2.3.5"
    const val navigation_fragment = "androidx.navigation:navigation-fragment:$navigationVersion"
    const val navigation_ui_ktx = "androidx.navigation:navigation-ui-ktx:$navigationVersion"

    /**
     * 图片加载框架Glide
     */
    private const val glideVersion = "4.11.0"
    const val glide = "com.github.bumptech.glide:glide:$glideVersion"
    //kotlin加上这个
    const val glide_compiler = "com.github.bumptech.glide:compiler:$glideVersion"
    const val lifecycle_compiler = "android.arch.lifecycle:compiler:1.0.0"

    /**
     * paging
     */
    private const val pagingVersion = "3.1.0"
    const val paging = "androidx.paging:paging-runtime:$pagingVersion"

    /**
     * arouter
     */
    private const val arouterVersion = "1.5.2"
    const val arouter = "com.alibaba:arouter-api:$arouterVersion"
    const val arouter_compiler = "com.alibaba:arouter-compiler:$arouterVersion"


    /**
     * rxjava
     */
    private const val rxjavaVersion = "3.0.0"
    const val rxandroid = "io.reactivex.rxjava3:rxandroid:$rxjavaVersion"
    const val rxjava =  "io.reactivex.rxjava3:rxjava:$rxjavaVersion"


    /**
     * eventbus
     */
    const val eventbus = "org.greenrobot:eventbus:3.3.1"
}